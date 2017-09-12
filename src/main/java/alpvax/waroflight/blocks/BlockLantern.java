package alpvax.waroflight.blocks;

import alpvax.waroflight.core.WarOfLightMod;
import alpvax.waroflight.emotions.EnumEmotion;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class BlockLantern extends Block
{
	public static final PropertyEnum<EnumEmotion> VARIANT = PropertyEnum.create("variant", EnumEmotion.class);

	public BlockLantern()
	{
		super(Material.IRON);
		setCreativeTab(WarOfLightMod.creativeTab);
		setHardness(2.0F);
		setSoundType(SoundType.METAL);
		setDefaultState(blockState.getBaseState().withProperty(VARIANT, EnumEmotion.WILLPOWER));
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(VARIANT, EnumEmotion.all_values[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(VARIANT).ordinal();
	}
}
