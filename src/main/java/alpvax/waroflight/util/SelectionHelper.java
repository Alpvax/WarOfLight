package alpvax.waroflight.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.SaveHandlerMP;
import net.minecraftforge.common.DimensionManager;
import alpvax.waroflight.core.EnumEmotion;
import alpvax.waroflight.core.WOLPlayer;


public class SelectionHelper
{
	/**
	 * @return The player ids with the hidden level of a certain colour
	 */
	public static Map<UUID, Integer> getPlayers(EnumEmotion e)
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

	public static List<WOLPlayer> listAllPlayers()
	{
		List<WOLPlayer> list = new ArrayList<WOLPlayer>();
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
					list.add(WOLPlayer.get(id));
				}
			}
		}
		catch(Exception ex)
		{
		}
		return list;
	}

	public static List<WOLPlayer> sortedList(final EnumEmotion e)
	{
		List<WOLPlayer> list = listAllPlayers();
		Collections.sort(list, new Comparator<WOLPlayer>()
		{
			@Override
			public int compare(WOLPlayer arg0, WOLPlayer arg1)
			{
				return arg0.getLevel(e) - arg1.getLevel(e);
			}

				});
		return list;
	}
}
