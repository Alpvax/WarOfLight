package alpvax.waroflight.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import alpvax.waroflight.core.EnumEmotion;
import alpvax.waroflight.core.WarOfLightMod;


public class BlockLantern extends Block
{
	public static final PropertyEnum VARIANT = PropertyEnum.create("variant", EnumEmotion.class, EnumEmotion.values);

	public BlockLantern()
	{
		super(Material.iron);
		setCreativeTab(WarOfLightMod.creativeTab);
		setHardness(2.0F);
		setStepSound(soundTypeMetal);
		setDefaultState(blockState.getBaseState().withProperty(VARIANT, EnumEmotion.WILLPOWER));
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(VARIANT, EnumEmotion.values[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumEmotion)state.getValue(VARIANT)).ordinal();
	}
}
