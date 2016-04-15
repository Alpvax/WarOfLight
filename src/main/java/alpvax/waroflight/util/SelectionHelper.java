package alpvax.waroflight.util;

import static alpvax.waroflight.capabilities.CapabilityWOLHandler.WOL_EMOTION_CAPABILITY;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import alpvax.waroflight.emotions.EnumEmotion;
import alpvax.waroflight.emotions.LanternState;
import alpvax.waroflight.items.WOLItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.storage.SaveHandlerMP;
import net.minecraftforge.common.DimensionManager;


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

	public static List<EntityPlayer> listAllPlayers()
	{
		List<EntityPlayer> list = new ArrayList<>();
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

	public static List<EntityPlayer> sortedList(final EnumEmotion e)
	{
		List<EntityPlayer> list = listAllPlayers();
		Collections.sort(list, new Comparator<EntityPlayer>()
		{
			@Override
			public int compare(EntityPlayer arg0, EntityPlayer arg1)
			{
				return arg0.getCapability(WOL_EMOTION_CAPABILITY, null).getLevel(e) - arg1.getCapability(WOL_EMOTION_CAPABILITY, null).getLevel(e);
			}

		});
		return list;
	}

	public void giveRing(EntityPlayer player, EnumEmotion e)
	{
		if(player != null && player.inventory.addItemStackToInventory(new ItemStack(WOLItems.ring, 1, e.ordinal())))
		{
			for(Object o : player.worldObj.playerEntities)
			{
				EntityPlayer p = (EntityPlayer)o;
				p.addChatComponentMessage(new TextComponentTranslation("lantern.aquired." + e.name().toLowerCase(Locale.ENGLISH), p.getDisplayNameString()));
				lanternStates.get(e).mastered = true;
				if(e != EnumEmotion.LIFE)
				{
					for(LanternState l : lanternStates.values())
					{
						if(!l.mastered)
						{
							return;
						}
					}
					giveRing(player, EnumEmotion.LIFE);
				}
			}
		}
	}

	public void removeRing(EntityPlayer player, EnumEmotion e)
	{
		if(player != null)
		{
			ItemStack ring = new ItemStack(WOLItems.ring, 1, e.ordinal());
			for(int i = 0; i < player.inventory.mainInventory.length; ++i)
			{
				if(ring.isItemEqual(player.inventory.getStackInSlot(i)))
				{
					player.inventory.setInventorySlotContents(i, null);
				}
			}
		}
	}
}
