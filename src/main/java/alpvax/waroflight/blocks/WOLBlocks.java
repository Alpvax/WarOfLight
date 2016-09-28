package alpvax.waroflight.blocks;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WOLBlocks
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
