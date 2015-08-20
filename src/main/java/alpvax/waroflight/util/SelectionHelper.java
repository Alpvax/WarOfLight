package alpvax.waroflight.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.SaveHandlerMP;
import net.minecraftforge.common.DimensionManager;
import alpvax.waroflight.core.EnumEmotion;
import alpvax.waroflight.core.WOLPlayer;


public class SelectionHelper
{
	/**
	 * @return The players with a ring of a certain colour
	 */
	public static Map<EntityPlayer, Integer> getPlayers(EnumEmotion e)
	{
		Map<EntityPlayer, Integer> map = new HashMap<EntityPlayer, Integer>();
		return map;
	}

	private static Map<UUID, Integer> loopThroughPlayers(EnumEmotion e)
	{
		Map<UUID, Integer> map = new HashMap<UUID, Integer>();
		SaveHandlerMP saveHandler = (SaveHandlerMP)DimensionManager.getWorld(0).getSaveHandler();
		NBTTagCompound nbt;

		try
		{
			File players = new File(saveHandler.getWorldDirectory(), "players");
			for(File f : players.listFiles())
			{
				nbt = CompressedStreamTools.read(f);
				UUID id = null;
				if(nbt.hasKey("UUIDMost", 4) && nbt.hasKey("UUIDLeast", 4))
				{
					id = new UUID(nbt.getLong("UUIDMost"), nbt.getLong("UUIDLeast"));
				}
				else if(nbt.hasKey("UUID", 8))
				{
					id = UUID.fromString(nbt.getString("UUID"));
				}
				if(id != null && nbt.hasKey(WOLPlayer.TAG_LANTERN_STATES))
				{
					WOLPlayer p = WOLPlayer.get(id);
					map.put(id, p.getLevel(e));
				}
			}
		}
		catch(Exception ex)
		{
		}
		return map;
	}
}
