package eiteam.esteemedinnovation.block;

import eiteam.esteemedinnovation.api.wrench.IWrenchable;
import eiteam.esteemedinnovation.api.block.BlockSteamTransporter;
import eiteam.esteemedinnovation.init.blocks.SteamMachineryBlocks;
import eiteam.esteemedinnovation.tile.TileEntityThumper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockThumper extends BlockSteamTransporter implements IWrenchable {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    // Because the defaults variant cannot have submodels :|
    private static final PropertyBool ALWAYS_TRUE = PropertyBool.create("always_true");

    public BlockThumper() {
        super(Material.IRON);
        setHardness(3.5F);
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, ALWAYS_TRUE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.withProperty(ALWAYS_TRUE, true);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block) {
        BlockPos above = pos.up();
        TileEntityThumper tile = (TileEntityThumper) world.getTileEntity(pos);
        if (world.getBlockState(above).getBlock() != SteamMachineryBlocks.Blocks.THUMPER_DUMMY.getBlock() && tile != null) {
            if (!world.isRemote) {
                dropBlockAsItem(world, pos, getDefaultState(), 0);
            }
            world.setBlockToAir(pos);
        }
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        // No need to break the rest of the Thumper dummies as that is handled by BlockThumperDummy#breakBlock.
        BlockPos dummyPos = pos.up();
        if (world.getBlockState(dummyPos).getBlock() == SteamMachineryBlocks.Blocks.THUMPER_DUMMY.getBlock()) {
            world.setBlockToAir(dummyPos);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase elb, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, elb.getHorizontalFacing().getOpposite()), 2);
        for (int y = 1; y < 4; y++) {
            BlockPos dummyPos = pos.up(y);
            world.setBlockState(dummyPos, SteamMachineryBlocks.Blocks.THUMPER_DUMMY.getBlock().getDefaultState());
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityThumper();
    }

    @Override
    public boolean onWrench(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, IBlockState state, float hitX, float hitY, float hitZ) {
        if (facing.getAxis() == EnumFacing.Axis.Y) {
            return false;
        }
        world.setBlockState(pos, state.withProperty(FACING, facing.getOpposite()), 2);
        return true;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
}
