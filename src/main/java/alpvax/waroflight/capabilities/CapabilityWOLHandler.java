package alpvax.waroflight.capabilities;

import java.util.concurrent.Callable;

import alpvax.waroflight.emotions.EnumEmotion;
import alpvax.waroflight.emotions.IEmotionHandler;
import alpvax.waroflight.emotions.LanternState;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityWOLHandler
{
	@CapabilityInject(IEmotionHandler.class)
	public static Capability<IEmotionHandler> WOL_EMOTION_CAPABILITY = null;

	public static void register()
	{
		CapabilityManager.INSTANCE.register(IEmotionHandler.class, new Capability.IStorage<IEmotionHandler>()
		{
			@Override
			public NBTBase writeNBT(Capability<IEmotionHandler> capability, IEmotionHandler instance, EnumFacing side)
			{
				NBTTagCompound nbt = new NBTTagCompound();
				for(EnumEmotion key : EnumEmotion.values)
				{
					LanternState l = instance.getLanternState(key);
					nbt.setTag(key.name(), l.serializeNBT());
				}
				return nbt;
			}

			@Override
			public void readNBT(Capability<IEmotionHandler> capability, IEmotionHandler instance, EnumFacing side, NBTBase base)
			{
				for(EnumEmotion key : EnumEmotion.values)
				{
					NBTTagCompound nbt = (NBTTagCompound)base;
					instance.getLanternState(key).deserializeNBT(nbt.getCompoundTag(key.name()));
				}
			}
		}, new Callable<IEmotionHandler>()
		{
			@Override
			public IEmotionHandler call() throws Exception
			{
				return new WOLEmotionHandler(null);
			}
		});
	}
}
