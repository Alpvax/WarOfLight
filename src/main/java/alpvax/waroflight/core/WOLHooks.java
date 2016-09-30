package alpvax.waroflight.core;

import alpvax.characteroverhaul.api.character.ICharacter;
import alpvax.waroflight.emotions.EnumEmotion;
import alpvax.waroflight.util.ValueHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;


public class WOLHooks
{
	/*TODO:create packet and send to player: sendTo(msg, player)
	@SubscribeEvent
	public void onJoinWorld(EntityJoinWorldEvent e)
	{
		if(e.entity instanceof EntityPlayerMP)
		{
			WOLPlayer.get((EntityPlayer)e.entity).getDataForClient();
		}
	}*/

	@SubscribeEvent
	public void onDeath(LivingDeathEvent event)
	{
		Entity e = event.getSource().getEntity();
		EntityLivingBase target = event.getEntityLiving();
		if(e != null && e.hasCapability(ICharacter.CAPABILITY, null))
		{
			e.getCapability(ICharacter.CAPABILITY, null).addSkillExperience(EnumEmotion.RAGE.getSkill(), ValueHelper.getRageForEntity(target));
		}
		if(target.hasCapability(ICharacter.CAPABILITY, null))
		{
			target.getCapability(ICharacter.CAPABILITY, null).addSkillExperience(EnumEmotion.DEATH.getSkill(), ValueHelper.getDeathExperience(target));
		}
	}

	@SubscribeEvent
	public void onPickup(ItemPickupEvent event)
	{
		if(event.player.hasCapability(ICharacter.CAPABILITY, null))
		{
			event.player.getCapability(ICharacter.CAPABILITY, null).addSkillExperience(EnumEmotion.GREED.getSkill(), ValueHelper.getGreedForStack(event.pickedUp.getEntityItem()));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public void onDrop(ItemTossEvent event)
	{
		if(event.getPlayer().hasCapability(ICharacter.CAPABILITY, null))
		{
			event.getPlayer().getCapability(ICharacter.CAPABILITY, null).addSkillExperience(EnumEmotion.GREED.getSkill(), -ValueHelper.getGreedForStack(event.getEntityItem().getEntityItem()));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onDrop(LivingDropsEvent event)
	{
		Entity e = event.getEntity();
		if(e.hasCapability(ICharacter.CAPABILITY, null))
		{
			ICharacter character = e.getCapability(ICharacter.CAPABILITY, null);
			for(EntityItem item : event.getDrops())
			{
				character.addSkillExperience(EnumEmotion.GREED.getSkill(), -ValueHelper.getGreedForStack(item.getEntityItem()));
			}
		}
	}

	/*TODO:Add/remove rings
	@SubscribeEvent
	public void onTick(PlayerTickEvent e)
	{
		if(e.phase == Phase.END)
		{
			WOLPlayer p = WOLPlayer.get(e.player);
			if(p != null)
			{
				for(EnumEmotion ee : EnumEmotion.values)
				{
					if(!p.hasRing(ee) && p.getLevel(ee) >= ee.getThreshold(ee) && e.player.worldObj.rand.nextInt(2400) == 0)//Average once every 2 mins
					{
						List<WOLPlayer> list = SelectionHelper.sortedList(ee);
						for(int i = 0; i < ConfigHelper.getMaxPlayers(ee); i++)
						{
							WOLPlayer wp = list.get(i);
							if(wp == p)
							{
								//TODO:p.giveRing(ee);
								break;
							}
						}
					}
				}
			}
		}
	}*/
}
