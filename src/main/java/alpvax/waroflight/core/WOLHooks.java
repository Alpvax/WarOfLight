package alpvax.waroflight.core;

import static alpvax.waroflight.capabilities.CapabilityWOLHandler.WOL_EMOTION_CAPABILITY;

import alpvax.waroflight.capabilities.WOLCapabilityProvider;
import alpvax.waroflight.emotions.EnumEmotion;
import alpvax.waroflight.emotions.IEmotionHandler;
import alpvax.waroflight.util.ConfigHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;


public class WOLHooks
{
	@SubscribeEvent
	public void attachCapabilities(AttachCapabilitiesEvent.Entity event)
	{
		if(event.getEntity() instanceof EntityPlayer)
		{
			event.addCapability(WOLConstants.WOL_CAPABILITY, new WOLCapabilityProvider((EntityPlayer)event.getEntity()));
		}
	}

	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone e)
	{
		IEmotionHandler e1 = e.getEntityPlayer().getCapability(WOL_EMOTION_CAPABILITY, null);
		IEmotionHandler e2 = e.getOriginal().getCapability(WOL_EMOTION_CAPABILITY, null);
		e1.cloneFrom(e2);
		e1.addLevel(EnumEmotion.DEATH, ConfigHelper.getDeathModifier());
		//TODO: on death. WOLPlayer.get(e.entityPlayer).cloneOnDeath(WOLPlayer.get(e.original));
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
	public void onKill(LivingDeathEvent e)
	{
		Entity e1 = e.getSource().getEntity();
		if(e1 != null && e1 instanceof EntityPlayer)
		{
			e1.getCapability(WOL_EMOTION_CAPABILITY, null).addLevel(EnumEmotion.RAGE, ConfigHelper.getRageForEntity(e.getEntityLiving()));
		}
	}

	@SubscribeEvent
	public void onPickup(ItemPickupEvent e)
	{
		e.player.getCapability(WOL_EMOTION_CAPABILITY, null).addLevel(EnumEmotion.GREED, ConfigHelper.getGreedForStack(e.pickedUp.getEntityItem()));
	}

	@SubscribeEvent
	public void onDrop(ItemTossEvent e)
	{
		e.getPlayer().getCapability(WOL_EMOTION_CAPABILITY, null).addLevel(EnumEmotion.GREED, -ConfigHelper.getGreedForStack(e.getEntityItem().getEntityItem()));
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
					if(!p.hasRing(ee) && p.getLevel(ee) >= ConfigHelper.getThreshold(ee) && e.player.worldObj.rand.nextInt(2400) == 0)//Average once every 2 mins
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
