package alpvax.waroflight.capabilities;

import java.util.HashMap;
import java.util.Map;

import alpvax.waroflight.emotions.EnumEmotion;
import alpvax.waroflight.emotions.IEmotionHandler;
import alpvax.waroflight.emotions.LanternState;
import net.minecraft.entity.player.EntityPlayer;

public class WOLEmotionHandler implements IEmotionHandler
{
	private final EntityPlayer player;
	private final Map<EnumEmotion, LanternState> lanternStates = new HashMap<EnumEmotion, LanternState>();

	public WOLEmotionHandler(EntityPlayer entity)
	{
		player = entity;
		for(EnumEmotion e : EnumEmotion.values)
		{
			lanternStates.put(e, new LanternState());
		}
	}

	@Override
	public EntityPlayer getPlayer()
	{
		return player;
	}

	@Override
	public LanternState getLanternState(EnumEmotion emotion)
	{
		return lanternStates.get(emotion);
	}

	@Override
	public int getLevel(EnumEmotion emotion)
	{
		return getLanternState(emotion).getLevel();
	}

	@Override
	public boolean hasMastered(EnumEmotion emotion)
	{
		return getLanternState(emotion).hasMastered();
	}

	@Override
	public void addLevel(EnumEmotion emotion, int amount)
	{
		getLanternState(emotion).addLevel(amount);
	}

	@Override
	public void cloneFrom(IEmotionHandler oldHandler)
	{
		for(EnumEmotion e : EnumEmotion.values)
		{
			getLanternState(e).deserializeNBT(oldHandler.getLanternState(e).serializeNBT());
		}
	}
}
