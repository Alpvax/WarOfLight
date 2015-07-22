package alpvax.wayoflight.items;

import java.util.List;
import java.util.Locale;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import alpvax.wayoflight.core.EnumLanternColour;
import alpvax.wayoflight.core.WOLPlayer;


public class ItemPowerLantern extends Item// implements IItemPowerProvider
{
	private static final String TAG_ACTIVE_COLOUR = "LanternColour";

	public ItemPowerLantern()
	{
		GameRegistry.registerItem(this, "lantern");
		setHasSubtypes(true);
		setMaxDamage(0);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabCombat);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int i = stack.getMetadata();
		return super.getUnlocalizedName() + "." + EnumLanternColour.values[i].name().toLowerCase(Locale.ENGLISH);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		for(EnumLanternColour c : EnumLanternColour.values)
		{
			if(advanced || c == getEffectiveColour(stack))
			{
				tooltip.add(c.chatColour() + I18n.format("lantern." + c.name().toLowerCase(Locale.ENGLISH) + ".charge") + ": " + WOLPlayer.get(playerIn).getLevel(c));
			}
		}
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List subItems)
	{
		for(EnumLanternColour c : EnumLanternColour.values)
		{
			if(c != EnumLanternColour.WHITE)//Do not add white to creative menu
			{
				subItems.add(new ItemStack(itemIn, 1, c.ordinal()));
			}
		}
	}

	/*TODO: implement IItemPowerProvider
	@Override
	public void itemAdded(Provider provider, EntityPlayer player)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public String itemKey()
	{
		return null;//TODO: colour.name();
	}

	@Override
	public Provider getProviderFromStack(ItemStack stack)
	{
		return null;// TODO Auto-generated method stub
	}*/

	public EnumLanternColour getEffectiveColour(ItemStack stack)
	{
		int i = stack.getItemDamage();
		if(i != EnumLanternColour.INDIGO.ordinal() && i != EnumLanternColour.WHITE.ordinal())
		{
			return EnumLanternColour.values[i];
		}
		NBTTagCompound nbt = stack.getTagCompound();
		return EnumLanternColour.values[nbt.hasKey(TAG_ACTIVE_COLOUR) ? nbt.getInteger(TAG_ACTIVE_COLOUR) : i];
	}
}
