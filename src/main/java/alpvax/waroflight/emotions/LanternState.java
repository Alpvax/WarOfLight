package alpvax.waroflight.emotions;

import alpvax.waroflight.core.WOLConstants;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class LanternState implements INBTSerializable<NBTTagCompound>
{
	private int level = 0;
	private boolean mastered = false;

	public boolean hasMastered()
	{
		return mastered;
	}

	public int getLevel()
	{
		return level;
	}

	public void addLevel(int amount)
	{
		level += amount;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger(WOLConstants.TAG_LEVEL, level);
		nbt.setBoolean(WOLConstants.TAG_MASTERED, mastered);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		level = nbt.getInteger(WOLConstants.TAG_LEVEL);
		mastered = nbt.getBoolean(WOLConstants.TAG_MASTERED);
	}

}
