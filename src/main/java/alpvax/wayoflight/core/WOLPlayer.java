package alpvax.wayoflight.core;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import alpvax.playerpowers.api.player.PoweredPlayer;
import alpvax.wayoflight.items.WOLItems;


public class WOLPlayer implements IExtendedEntityProperties
{
	private static final String TAG_LANTERN_STATES = "LanternStates";
	private static final String TAG_LEVEL = "Level";
	private static final String TAG_MASTERED = "Mastered";

	private Map<EnumLanternColour, LanternState> lanternStates = new HashMap<EnumLanternColour, LanternState>();

	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		for(EnumLanternColour key : lanternStates.keySet())
		{
			compound.setTag(key.name(), lanternStates.get(key).writeToNBT(new NBTTagCompound()));
		}
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		for(EnumLanternColour key : lanternStates.keySet())
		{
			lanternStates.get(key).readFromNBT(compound);
		}
	}

	@Override
	public void init(Entity entity, World world)
	{
		for(EnumLanternColour e : EnumLanternColour.values)
		{
			lanternStates.put(e, new LanternState());
		}
	}

	public void giveLantern(EntityPlayer player, EnumLanternColour c)
	{
		if(player.inventory.addItemStackToInventory(new ItemStack(WOLItems.lantern, 1, c.ordinal())))
		{
			for(Object o : player.worldObj.playerEntities)
			{
				EntityPlayer p = (EntityPlayer)o;
				p.addChatComponentMessage(new ChatComponentTranslation("lantern.aquired." + c.name().toLowerCase(Locale.ENGLISH), p.getDisplayNameString()));
				lanternStates.get(c).mastered = true;
				for(LanternState l : lanternStates.values())
				{
					if(!l.mastered)
					{
						return;
					}
				}
				giveLantern(player, EnumLanternColour.WHITE);
			}
		}
	}

	public Object getLevel(EnumLanternColour c)
	{
		return lanternStates.get(c).level;
	}

	public class LanternState
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
		player.registerExtendedProperties(TAG_LANTERN_STATES, new PoweredPlayer());
	}

	public static final WOLPlayer get(EntityPlayer player)
	{
		return (WOLPlayer)player.getExtendedProperties(TAG_LANTERN_STATES);
	}
}
