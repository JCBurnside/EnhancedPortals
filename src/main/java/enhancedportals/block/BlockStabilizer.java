package enhancedportals.block;

import enhancedportals.network.CommonProxy;
import enhancedportals.tile.TileStabilizer;
import enhancedportals.tile.TileStabilizerMain;
import enhancedportals.utility.ConnectedTexturesDetailed;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static enhancedportals.block.BlockStabilizer.StabilizerPart.MAIN;
import static enhancedportals.block.BlockStabilizer.StabilizerPart.STABILIZER;

public class BlockStabilizer extends BlockContainer
{
    public static BlockStabilizer instance;
    public static ConnectedTexturesDetailed connectedTextures;

    public static final PropertyEnum STABALIZER_PART = PropertyEnum.create("stabilizer", BlockStabilizer.StabilizerPart.class);

    public enum StabilizerPart implements IStringSerializable {
        STABILIZER(0,"stabilizaer"),
        MAIN(1, "main");

        private String name;
        private int id;

        private StabilizerPart(int id, String name){
            this.name = name;
            this.id = id;
        }

        public int getMetadata() {return this.id;}

        @Override
        public String getName() {
            return name;
        }
    }

    public BlockStabilizer()
    {
        super(Material.ROCK);
        instance = this;
        setHardness(5);
        setResistance(2000);
        setUnlocalizedName("dbs");
        setRegistryName("bridge_stabilizer");
        setSoundType(SoundType.STONE);
        setCreativeTab(CommonProxy.creativeTab);
        connectedTextures = new ConnectedTexturesDetailed("enhancedportals:bridge/%s", this, -1);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileStabilizer)
        {
            ((TileStabilizer) tile).breakBlock();
        }
        else if (tile instanceof TileStabilizerMain)
        {
            ((TileStabilizerMain) tile).breakBlock();
        }

        super.breakBlock(world, pos, state);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        if (metadata == STABILIZER.ordinal())
        {
            return new TileStabilizer();
        }
        else if (metadata == MAIN.ordinal())
        {
            return new TileStabilizerMain();
        }

        return null;
    }


    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileStabilizer)
        {
            return ((TileStabilizer) tile).activate(player);
        }
        else if (tile instanceof TileStabilizerMain)
        {
            return ((TileStabilizerMain) tile).activate(player);
        }

        return false;
    }

}
