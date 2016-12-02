package enhancedportals.block;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import enhancedportals.network.CommonProxy;
import enhancedportals.tile.*;
import enhancedportals.utility.ConnectedTexturesDetailed;
import enhancedportals.utility.IDismantleable;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;

import javax.annotation.Nullable;
import java.util.List;

import static enhancedportals.Reference.Dependencies.MODID_COMPUTERCRAFT;

@InterfaceList(value = {@Interface(iface = "dan200.computercraft.api.peripheral.IPeripheralProvider", modid = MODID_COMPUTERCRAFT)})
public class BlockFrame extends BlockContainer implements IDismantleable, IPeripheralProvider
{
    public static BlockFrame instance;
    public static ConnectedTexturesDetailed connectedTextures;
    //public static IIcon[] overlayIcons;
    public static int PORTAL_CONTROLLER = 1, REDSTONE_INTERFACE = 2, NETWORK_INTERFACE = 3, DIALLING_DEVICE = 4, UNUSED = 5, MODULE_MANIPULATOR = 6, TRANSFER_FLUID = 7, TRANSFER_ITEM = 8, TRANSFER_ENERGY = 9;
    public static int FRAME_TYPES = 10;

    public BlockFrame(String n)
    {
        super(Material.ROCK);
        instance = this;
        setCreativeTab(CommonProxy.creativeTab);
        setHardness(5);
        setResistance(2000);
        setUnlocalizedName(n);
//      setRegistryName("enhancedportals","frame");
        setSoundType(SoundType.STONE);
        connectedTextures = new ConnectedTexturesDetailed("enhancedportals:frame/%s", this, -1);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity t = world.getTileEntity(pos);

        if (t != null && t instanceof TileFrame)
        {
            ((TileFrame) t).breakBlock(world, pos, state);
        }

        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean canProvidePower(IBlockState state)
    {
        return true;
    }

    /*@Override
    public boolean canRenderInPass(int pass)
    {
        ClientProxy.renderPass = pass;
        return pass < 2;
    }*/


    //todo color multiplier?
    /*@Override
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
    {
        TileEntity tile = blockAccess.getTileEntity(x, y, z);

        if (tile instanceof TileFrame)
        {
            return ((TileFrame) tile).getColour();
        }

        return 0xFFFFFF;
    }*/

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        if (metadata == 0)
        {
            return new TileFrameBasic();
        }
        else if (metadata == PORTAL_CONTROLLER)
        {
            return new TileController();
        }
        else if (metadata == REDSTONE_INTERFACE)
        {
            return new TileRedstoneInterface();
        }
        else if (metadata == NETWORK_INTERFACE)
        {
            return new TileNetworkInterface();
        }
        else if (metadata == DIALLING_DEVICE)
        {
            return new TileDialingDevice();
        }
        else if (metadata == MODULE_MANIPULATOR)
        {
            return new TilePortalManipulator();
        }
        else if (metadata == TRANSFER_FLUID)
        {
            return new TileTransferFluid();
        }
        else if (metadata == TRANSFER_ITEM)
        {
            return new TileTransferItem();
        }
        else if (metadata == TRANSFER_ENERGY)
        {
            return new TileTransferEnergy();
        }

        return null;
    }

    @Override
    public void dismantleBlock(EntityPlayer player, World world, BlockPos pos)
    {
        ItemStack dropBlock = new ItemStack(this, 1, world.getBlockState(pos).getBlock().getMetaFromState(getDefaultState()));

        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileFrame)
        {
            ((TileFrame) tile).onBlockDismantled(pos);
        }

        world.setBlockToAir(pos);

        if (dropBlock != null)
        {
            float f = 0.3F;
            double x2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
            double y2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
            double z2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
            EntityItem item = new EntityItem(world, pos.getX() + x2, pos.getY() + y2, pos.getZ() + z2, dropBlock);
            //item.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(item);
        }
    }

//    @Override
//    @Optional.Method(modid = Reference.Dependencies.MODID_COMPUTERCRAFT)
//    public IPeripheral getPeripheral(World world, int x, int y, int z, int side)
//    {
//        TileEntity t = world.getTileEntity();
//
//        if (t != null && (t instanceof TileController || t instanceof TileNetworkInterface || t instanceof TileDialingDevice || t instanceof TileTransferEnergy || t instanceof TileTransferFluid || t instanceof TileTransferItem))
//        {
//            return (IPeripheral) t;
//        }
//
//        return null;
//    }*/

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this, 1);
    }

    /*@Override
    public int getRenderBlockPass()
    {
        return 1;
    }*/

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void getSubBlocks(Item par1, CreativeTabs creativeTab, List list)
    {
        for (int i = 0; i < FRAME_TYPES; i++)
        {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public boolean isBlockSolid(IBlockAccess p_149747_1_, BlockPos pos, EnumFacing side)
    {
        return false;
    }

   /* @Override
    public boolean isBlockNormalCube()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }*/

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
    {

        return state.getMaterial().isOpaque() && state.isFullCube() && !state.canProvidePower();
    }

    public int isProvidingStrongPower(IBlockAccess blockAccess, BlockPos pos, int side)
    {
        TileEntity tile = blockAccess.getTileEntity(pos);

        if (tile instanceof TileRedstoneInterface)
        {
            return ((TileRedstoneInterface) tile).isProvidingPower(side);
        }

        return 0;
    }

    public int isProvidingWeakPower(IBlockAccess blockAccess, BlockPos pos, int side)
    {
        TileEntity tile = blockAccess.getTileEntity(pos);

        if (tile instanceof TileRedstoneInterface)
        {
            return ((TileRedstoneInterface) tile).isProvidingPower(side);
        }

        return 0;
    }

    @Override
    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return true;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileFrame)
        {
            return ((TileFrame) tile).activate(player, player.inventory.getCurrentItem(), pos);
        }

        return false;
    }

    public void onNeighborBlockChange(IBlockAccess world, BlockPos pos, BlockPos neighbor, EnumFacing side)
    {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileRedstoneInterface)
        {
            ((TileRedstoneInterface) tile).onNeighborBlockChange(pos, side);
        }
        else if (tile instanceof TileFrameTransfer)
        {
            ((TileFrameTransfer) tile).onNeighborChanged();
        }
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        if (blockAccess.getBlockState(pos).getBlock() == this) return false;
        return super.shouldSideBeRendered(state, blockAccess, pos, side);
    }

    @Override
    public IPeripheral getPeripheral(World world, int x, int y, int z, int side)
    {
        return null;
    }
}
