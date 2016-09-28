package alpvax.waroflight.core;

import alpvax.characteroverhaul.api.character.ICharacter;
import alpvax.waroflight.emotions.EnumEmotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;


public class WOLHooks
{
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event)
	{
		EntityPlayer player = event.getEntityPlayer();
		if(player.hasCapability(ICharacter.CAPABILITY, null))
		{
			player.getCapability(ICharacter.CAPABILITY, null).addSkillExperience(EnumEmotion.DEATH.getSkill(), 1000F);//TODO:amount
		}
	}

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
	public void onKill(LivingDeathEvent event)
	{
		Entity e = event.getSource().getEntity();
		if(e != null && e.hasCapability(ICharacter.CAPABILITY, null))
		{
			e.getCapability(ICharacter.CAPABILITY, null).addSkillExperience(EnumEmotion.RAGE, ConfigHelper.getRageForEntity(event.getEntityLiving()));
		}
	}

	@SubscribeEvent
	public void onPickup(ItemPickupEvent event)
	{
		if(event.player.hasCapability(ICharacter.CAPABILITY, null))
		{
			event.player.getCapability(ICharacter.CAPABILITY, null).addSkillExperience(EnumEmotion.GREED, ConfigHelper.getGreedForStack(event.pickedUp.getEntityItem()));
		}
	}

	@SubscribeEvent
	public void onDrop(ItemTossEvent event)
	{
		if(event.getPlayer().hasCapability(ICharacter.CAPABILITY, null))
		{
			event.getPlayer().getCapability(ICharacter.CAPABILITY, null).addSkillExperience(EnumEmotion.GREED, -ConfigHelper.getGreedForStack(event.getEntityItem().getEntityItem()));
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
