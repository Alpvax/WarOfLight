package alpvax.wayoflight.blocks;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class WOLBlocks
{
	public static Block rageFire;

	public WOLBlocks()
	{
		rageFire = GameRegistry.registerBlock(new BlockRageFire().setUnlocalizedName("rageFire"), "rageFire");
	}

}
