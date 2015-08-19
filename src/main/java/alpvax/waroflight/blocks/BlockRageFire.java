package alpvax.waroflight.blocks;

import static net.minecraft.block.BlockLiquid.LEVEL;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;


public class BlockRageFire extends BlockFire
{

	public BlockRageFire()
	{
		super();
	}

	/**
	 * Called when a neighboring block changes.
	 */
	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		/*int i = 0;
		IBlockState state1 = worldIn.getBlockState(pos.up());
		if(state1.getBlock().getMaterial() == Material.water)
		{
			i += ((Integer)state1.getValue(LEVEL)).intValue() + 1;
		}
		state1 = worldIn.getBlockState(pos.north());
		if(state1.getBlock().getMaterial() == Material.water)
		{
			i += ((Integer)state1.getValue(LEVEL)).intValue();
		}
		state1 = worldIn.getBlockState(pos.south());
		if(state1.getBlock().getMaterial() == Material.water)
		{
			i += ((Integer)state1.getValue(LEVEL)).intValue();
		}
		state1 = worldIn.getBlockState(pos.east());
		if(state1.getBlock().getMaterial() == Material.water)
		{
			i += ((Integer)state1.getValue(LEVEL)).intValue();
		}
		state1 = worldIn.getBlockState(pos.west());
		if(state1.getBlock().getMaterial() == Material.water)
		{
			i += ((Integer)state1.getValue(LEVEL)).intValue();
		}
		if(i > 3)
		{
			worldIn.setBlockToAir(pos);
			return;
		}*/
		destroyNearbyWater(worldIn, pos);
		super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
	}

	private void destroyNearbyWater(World world, BlockPos origin)
	{
		IBlockState state = world.getBlockState(origin.up());
		if(state.getBlock().getMaterial() == Material.water)
		{
			world.setBlockState(origin.up(), state.withProperty(LEVEL, Integer.valueOf(Math.max(0, ((Integer)state.getValue(LEVEL)).intValue() - 7))), 2);
		}
		for(BlockPos pos : new BlockPos[]{origin.up().north(), origin.up().south(), origin.up().east(), origin.up().west()})
		{
			state = world.getBlockState(pos);
			if(state.getBlock().getMaterial() == Material.water)
			{
				world.setBlockState(pos, Blocks.air.getDefaultState(), 3);
			}
		}
		for(BlockPos pos : new BlockPos[]{origin.north(), origin.south(), origin.east(), origin.west()})
		{
			state = world.getBlockState(pos);
			if(state.getBlock().getMaterial() == Material.water)
			{
				world.setBlockState(origin.up(), state.withProperty(LEVEL, Integer.valueOf(1)), 2);
			}
		}
		state = world.getBlockState(origin.down());
		if(state.getBlock().getMaterial() == Material.water)
		{
			world.setBlockState(origin.up(), state.withProperty(LEVEL, Integer.valueOf(Math.max(0, ((Integer)state.getValue(LEVEL)).intValue() - 5))), 2);
		}
	}
}
