package alpvax.waroflight.emotions;

import net.minecraft.entity.player.EntityPlayer;

public interface IEmotionHandler
{
	public EntityPlayer getPlayer();

	public LanternState getLanternState(EnumEmotion emotion);

	public boolean hasMastered(EnumEmotion emotion);

	public int getLevel(EnumEmotion emotion);

	public void addLevel(EnumEmotion emotion, int amount);

	public void cloneFrom(IEmotionHandler oldHandler);
}
