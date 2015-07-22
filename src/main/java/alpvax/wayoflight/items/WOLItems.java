package alpvax.wayoflight.items;

import net.minecraft.item.Item;


public class WOLItems
{
	public static Item lantern;

	public WOLItems()
	{
		lantern = new ItemPowerLantern().setUnlocalizedName("powerLantern");
	}
}
