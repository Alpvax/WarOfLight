package alpvax.waroflight.items;

import static alpvax.waroflight.capabilities.CapabilityWOLHandler.WOL_EMOTION_CAPABILITY;

import java.util.List;
import java.util.Locale;

import alpvax.waroflight.core.WarOfLightMod;
import alpvax.waroflight.emotions.EnumEmotion;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class ItemPowerRing extends Item// implements IItemPowerProvider
{
	private static final String TAG_ACTIVE_COLOUR = "LanternColour";

	public ItemPowerRing()
	{
		GameRegistry.registerItem(this, "lantern");
		setHasSubtypes(true);
		setMaxDamage(0);
		setMaxStackSize(1);
		setCreativeTab(WarOfLightMod.creativeTab);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int i = stack.getMetadata();
		return super.getUnlocalizedName() + "." + EnumEmotion.all_values[i].name().toLowerCase(Locale.ENGLISH);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		for(EnumEmotion e : EnumEmotion.all_values)
		{
			if(advanced || e == getEffectiveColour(stack))
			{
				tooltip.add(e.chatColourChar() + I18n.format("lantern." + e.name().toLowerCase(Locale.ENGLISH) + ".charge") + ": " + playerIn.getCapability(WOL_EMOTION_CAPABILITY, null).getLevel(e));
			}
		}
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List subItems)
	{
		for(EnumEmotion c : EnumEmotion.all_values)
		{
			if(c != EnumEmotion.LIFE)//Do not add life to creative menu
			{
				subItems.add(new ItemStack(itemIn, 1, c.ordinal()));
			}
		}
	}

	/*TOD O: implement IItemPowerProvider
	@Override
	public void itemAdded(Provider provider, EntityPlayer player)
	{
		// TOD O Auto-generated method stub
	}

	@Override
	public String itemKey()
	{
		return null;//TOD O: colour.name();
	}

	@Override
	public Provider getProviderFromStack(ItemStack stack)
	{
		return null;// TOD O Auto-generated method stub
	}*/

	public EnumEmotion getEffectiveColour(ItemStack stack)
	{
		int i = stack.getItemDamage();
		if(i != EnumEmotion.COMPASSION.ordinal() && i != EnumEmotion.LIFE.ordinal())
		{
			return EnumEmotion.all_values[i];
		}
		NBTTagCompound nbt = stack.getTagCompound();
		return EnumEmotion.all_values[nbt.hasKey(TAG_ACTIVE_COLOUR) ? nbt.getInteger(TAG_ACTIVE_COLOUR) : i];
	}
}
