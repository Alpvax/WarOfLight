package alpvax.waroflight.capabilities;

import alpvax.abilities.api.capabilities.SimpleCapabilityProvider;
import alpvax.waroflight.emotions.IEmotionHandler;
import net.minecraft.entity.player.EntityPlayer;

public class WOLCapabilityProvider extends SimpleCapabilityProvider<IEmotionHandler>
{
	public WOLCapabilityProvider(EntityPlayer player)
	{
		this(new WOLEmotionHandler(player));
	}
	public WOLCapabilityProvider(IEmotionHandler capabilityHandler)
	{
		super(capabilityHandler, CapabilityWOLHandler.WOL_EMOTION_CAPABILITY);
	}
}
