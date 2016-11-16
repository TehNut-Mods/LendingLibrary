package tehnut.lib.test.item;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tehnut.lib.annot.ModItem;
import tehnut.lib.annot.Used;
import tehnut.lib.iface.IMeshProvider;
import tehnut.lib.test.LendingLibraryTest;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@ModItem(name = "ItemMeshProviderTest")
@Used
public class ItemMeshProviderTest extends Item implements IMeshProvider {

    public ItemMeshProviderTest() {
        setUnlocalizedName("test.mesh");
        setCreativeTab(LendingLibraryTest.tabTest);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (world.isRemote)
            return super.onItemRightClick(world, player, hand);

        ItemStack stack = player.getHeldItem(hand);

        if (player.isSneaking()) {
            LendingLibraryTest.instance.getLocalizationHelper().sendLocalization((EntityPlayerMP) player, "test.key.ignore");
            LendingLibraryTest.instance.getLocalizationHelper().sendLocalization((EntityPlayerMP) player, "test.key.ignore.2", String.valueOf(System.currentTimeMillis()));
        }

        if (stack.getTagCompound() == null)
            stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setBoolean("true", !stack.getTagCompound().getBoolean("true"));

        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemMeshDefinition getMeshDefinition() {
        return new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                if (stack.getTagCompound() == null)
                    stack.setTagCompound(new NBTTagCompound());

                if (stack.getTagCompound().getBoolean("true"))
                    return new ModelResourceLocation(new ResourceLocation("lendinglibrary-test", "item/ItemMeshProviderTest"), "true=true");

                return new ModelResourceLocation(new ResourceLocation("lendinglibrary-test", "item/ItemMeshProviderTest"), "true=false");
            }
        };
    }

    @Override
    public List<String> getVariants() {
        List<String> ret = new ArrayList<String>();
        ret.add("true=true");
        ret.add("true=false");
        return ret;
    }

    @Nullable
    @Override
    public ResourceLocation getCustomLocation() {
        return null;
    }
}
