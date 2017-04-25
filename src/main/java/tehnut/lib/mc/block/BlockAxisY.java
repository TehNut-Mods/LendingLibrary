package tehnut.lib.mc.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAxisY extends BlockEnum<EnumFacing> {

    private final PlacementStyle placementStyle;

    public BlockAxisY(Material materialIn, PlacementStyle placementStyle) {
        super(materialIn, EnumFacing.class, "facing", EnumFacing.Plane.HORIZONTAL);

        this.placementStyle = placementStyle;
    }

    public BlockAxisY(Material material) {
        this(material, PlacementStyle.FACE_TOWARD);
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> subBlocks) {
        // To stop each facing having an itemblock
        subBlocks.add(new ItemStack(item));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        EnumFacing playerFacing = placementStyle.transform(placer.getHorizontalFacing());
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(getProperty(), playerFacing);
    }

    @Override
    public boolean hasCustomItem() {
        return true;
    }

    public enum PlacementStyle {
        FACE_TOWARD {
            @Override
            public EnumFacing transform(EnumFacing facing) {
                return facing.getOpposite();
            }
        },
        FACE_AWAY,
        ;

        public EnumFacing transform(EnumFacing facing) {
            return facing;
        }
    }
}
