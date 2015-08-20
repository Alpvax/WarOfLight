package alpvax.waroflight.core;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import alpvax.waroflight.util.ConfigHelper;
import alpvax.waroflight.util.SelectionHelper;


public class WOLHooks
{
	@SubscribeEvent
	public void onCreate(EntityConstructing e)
	{
		if(e.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)e.entity;
			if(WOLPlayer.get(player) == null)
			{
				WOLPlayer.register(player);
			}
		}
	}

	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone e)
	{
		if(e.wasDeath)
		{
			WOLPlayer.get(e.entityPlayer).addLevel(EnumEmotion.DEATH, ConfigHelper.getDeathModifier());
			//TODO: on death. WOLPlayer.get(e.entityPlayer).cloneOnDeath(WOLPlayer.get(e.original));
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
	public void onKill(LivingDeathEvent e)
	{
		Entity e1 = e.source.getEntity();
		if(e1 != null && e1 instanceof EntityPlayer)
		{
			WOLPlayer.get((EntityPlayer)e1).addLevel(EnumEmotion.RAGE, ConfigHelper.getRageForEntity(e.entityLiving));
		}
	}

	@SubscribeEvent
	public void onPickup(ItemPickupEvent e)
	{
		WOLPlayer.get(e.player).addLevel(EnumEmotion.GREED, ConfigHelper.getGreedForStack(e.pickedUp.getEntityItem()));
	}

	@SubscribeEvent
	public void onDrop(ItemTossEvent e)
	{
		WOLPlayer.get(e.player).addLevel(EnumEmotion.GREED, -ConfigHelper.getGreedForStack(e.entityItem.getEntityItem()));//TODO:Test -function = -(result)
	}

	@SubscribeEvent
	public void onTick(LivingUpdateEvent e)
	{
		if(e.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)e.entityLiving;
			WOLPlayer p = WOLPlayer.get(player);
			if(p != null)
			{
				for(EnumEmotion ee : EnumEmotion.values)
				{
					if(!p.hasRing(ee) && p.getLevel(ee) >= ConfigHelper.getThreshold(ee) && player.worldObj.rand.nextInt(2400) == 0)//Average once every 2 mins
					{
						List<WOLPlayer> list = SelectionHelper.sortedList(ee);
						for(int i = 0; i < ConfigHelper.getMaxPlayers(ee); i++)
						{
							WOLPlayer wp = list.get(i);
							if(wp == p)
							{
								p.giveRing(ee);
								break;
							}
						}
					}
				}
			}
		}
	}
}
