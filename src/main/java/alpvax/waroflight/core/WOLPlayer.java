package alpvax.waroflight.core;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import alpvax.waroflight.items.WOLItems;


public class WOLPlayer implements IExtendedEntityProperties
{
	public static final String TAG_LANTERN_STATES = "LanternStates";
	private static final String TAG_LEVEL = "Level";
	private static final String TAG_MASTERED = "Mastered";

	private EntityPlayer player = null;
	private Map<EnumEmotion, LanternState> lanternStates = new HashMap<EnumEmotion, LanternState>();

	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		for(EnumEmotion key : lanternStates.keySet())
		{
			compound.setTag(key.name(), lanternStates.get(key).writeToNBT(new NBTTagCompound()));
		}
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		for(EnumEmotion key : lanternStates.keySet())
		{
			lanternStates.get(key).readFromNBT(compound);
		}
	}

	public WOLPlayer()
	{
		for(EnumEmotion e : EnumEmotion.values)
		{
			lanternStates.put(e, new LanternState());
		}
	}

	@Override
	public void init(Entity entity, World world)
	{
		player = (EntityPlayer)entity;
	}

	public void giveRing(EnumEmotion e)
	{
		if(player != null && player.inventory.addItemStackToInventory(new ItemStack(WOLItems.ring, 1, e.ordinal())))
		{
			for(Object o : player.worldObj.playerEntities)
			{
				EntityPlayer p = (EntityPlayer)o;
				p.addChatComponentMessage(new ChatComponentTranslation("lantern.aquired." + e.name().toLowerCase(Locale.ENGLISH), p.getDisplayNameString()));
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
					giveRing(EnumEmotion.LIFE);
				}
			}
		}
	}

	public void removeRing(EnumEmotion e)
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

	public boolean hasRing(EnumEmotion e)
	{
		return lanternStates.get(e).mastered;
	}

	public int getLevel(EnumEmotion e)
	{
		return lanternStates.get(e).level;
	}

	public void addLevel(EnumEmotion e, int modifier)
	{
		lanternStates.get(e).level += modifier;
	}

	public static class LanternState
	{
		private int level = 0;
		private boolean mastered = false;

		public LanternState()
		{
		}

		public void readFromNBT(NBTTagCompound nbt)
		{
			level = nbt.getInteger(TAG_LEVEL);
			mastered = nbt.getBoolean(TAG_MASTERED);
		}

		public NBTTagCompound writeToNBT(NBTTagCompound nbt)
		{
			nbt.setInteger(TAG_LEVEL, level);
			nbt.setBoolean(TAG_MASTERED, mastered);
			return nbt;
		}
	}

	public static final void register(EntityPlayer player)
	{
		player.registerExtendedProperties(TAG_LANTERN_STATES, new WOLPlayer());
	}

	public static final WOLPlayer get(EntityPlayer player)
	{
		return (WOLPlayer)player.getExtendedProperties(TAG_LANTERN_STATES);
	}

	public static final WOLPlayer get(UUID playerID)
	{
		EntityPlayer player = (EntityPlayer)MinecraftServer.getServer().getConfigurationManager().uuidToPlayerMap.get(playerID);
		return player != null ? get(player) : new WOLPlayer();
	}
}
