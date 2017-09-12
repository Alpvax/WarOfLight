package alpvax.waroflight.capabilities;

import alpvax.waroflight.emotions.IEmotionHandler;
import net.minecraft.entity.player.EntityPlayer;

public class WOLCapabilityProvider extends SimpleCapabilityProvider<IEmotionHandler>
{
	private IEmotionHandler emotion;

	public WOLCapabilityProvider(EntityPlayer player)
	{
		this(new WOLEmotionHandler(player));
	}
	public WOLCapabilityProvider(IEmotionHandler capabilityHandler)
	{
		super(capabilityHandler, CapabilityWOLHandler.WOL_EMOTION_CAPABILITY);
	}
}
