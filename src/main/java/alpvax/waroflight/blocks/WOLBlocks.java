package alpvax.waroflight.blocks;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class WOLBlocks
{
	public static Block lantern;
	public static Block rageFire;

	public WOLBlocks()
	{
		lantern = GameRegistry.registerBlock(new BlockLantern().setUnlocalizedName("lantern"), "lantern");
		rageFire = GameRegistry.registerBlock(new BlockRageFire().setUnlocalizedName("rageFire"), "rageFire");
	}

}
