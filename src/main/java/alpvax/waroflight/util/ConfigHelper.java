package alpvax.waroflight.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import alpvax.waroflight.core.EnumEmotion;


public class ConfigHelper
{
	//TODO: Read from server config file.


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
			//TODO: different items provide different levels of greed
			//Awaiting EE3 update to 1.8: Math.sqrt(EnergyValueRegistry.getEnergyValue(stack)) * stack.stackSize;
			return stack.stackSize;
		}
		return 0;
	}

	/**
	 * @return The amount the death bar is increased by dying
	 */
	public static int getDeathModifier()
	{
		return 1000;
	}

	/**
	 * @return The maximum number of players of a certain colour
	 */
	public static int getMaxPlayers(EnumEmotion e)
	{
		return 3;
	}

	/**
	 * @return The level of a certain colour required to recieve a ring
	 */
	public static int getThreshold(EnumEmotion ee)
	{
		return ee == EnumEmotion.DEATH ? 8000 : 1000;
	}
}
