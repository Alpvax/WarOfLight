package alpvax.waroflight.items;

import alpvax.waroflight.blocks.BlockLantern;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WOLItems
{
	public static final Block lantern;

	private static final Block[] blocks = {lantern = new BlockLantern()};

	static
	{
		for(Block block : blocks)
		{
			GameRegistry.register(block);
		}
	}

}
