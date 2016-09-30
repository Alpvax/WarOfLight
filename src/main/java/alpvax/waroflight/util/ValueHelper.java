package alpvax.waroflight.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ValueHelper
{

	public static float getDeathExperience(EntityLivingBase entity)
	{
		// TODO Auto-generated method stub
		return 1000;
	}

	public static float getRageForEntity(EntityLivingBase entity)
	{
		return Math.round(entity.getMaxHealth());
	}

	public static float getGreedForStack(ItemStack stack)
	{
		if(stack != null)
		{
			//TODO: different items provide different levels of greed
			//Awaiting EE3 update to 1.8: Math.sqrt(EnergyValueRegistry.getEnergyValue(stack)) * stack.stackSize;
			return stack.stackSize;
		}
		return 0;
	}

}
