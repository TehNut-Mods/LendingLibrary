package tehnut.lib.mc.block;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
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

public class BlockDirectional extends BlockEnum<EnumFacing> {

    private final PlacementStyle placementStyle;

    public BlockDirectional(Material materialIn, PlacementStyle placementStyle, Predicate<EnumFacing> validFacing) {
        super(materialIn, EnumFacing.class, "facing", validFacing);

        this.placementStyle = placementStyle;
    }

    public BlockDirectional(Material material, PlacementStyle placementStyle) {
        this(material, placementStyle, Predicates.<EnumFacing>alwaysTrue());
    }

    public BlockDirectional(Material material) {
        this(material, PlacementStyle.FACE_TOWARD);
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> subBlocks) {
        // To stop each facing having an itemblock
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        EnumFacing playerFacing = placementStyle.transform(placer, EnumFacing.getDirectionFromEntityLiving(pos, placer));
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(getProperty(), playerFacing);
    }

    public enum PlacementStyle {
        FACE_TOWARD {
            @Override
            public EnumFacing transform(EntityLivingBase player, EnumFacing facing) {
                return facing.getOpposite();
            }
        },
        FACE_AWAY,
        ;

        public EnumFacing transform(EntityLivingBase player, EnumFacing facing) {
            return facing;
        }
    }
}
