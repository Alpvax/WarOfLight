package alpvax.waroflight.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;


public class ConfigHelper
{
	/**
	 * @return The amount the rage bar is increased for killing the entity
	 */
	public static int getRageForEntity(EntityLivingBase entity)
	{
		return Math.round(entity.getMaxHealth());
	}

	/**
	 * @return The amount the greed bar is increased by collecting the stack
	 */
	public static int getGreedForStack(ItemStack stack)
	{
		if(stack != null)
		{
			//TODO: different items provide different levels of greed, EE3 integration
			return stack.stackSize;
		}
		return 0;
	}

	/**
	 * @return The amount the death bar is increased by dying
	 */
	public static int getDeathModifier()
	{
		return 0;// TODO Auto-generated method stub
	}
}
