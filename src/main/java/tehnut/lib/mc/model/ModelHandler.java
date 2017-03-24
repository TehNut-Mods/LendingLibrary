package tehnut.lib.mc.model;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ModelHandler {

    @SuppressWarnings({"MethodCallSideOnly"})
    // Helper method for registering items/blocks and handling their
    public static <T extends IForgeRegistryEntry<T>> T register(T type) {
        if (type instanceof Item) {
            GameRegistry.register(type);
            if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
                Client.handleItemModel((Item) type);
        } else if (type instanceof Block) {
            GameRegistry.register(type);
            if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
                Client.handleBlockModel((Block) type);
        } else {
            GameRegistry.register(type);
        }

        return type;
    }

    @SuppressWarnings({"ConstantConditions"})
    @SideOnly(Side.CLIENT)
    public static class Client {

        public static void handleBlockModel(Block block) {
            Item itemBlock = Item.getItemFromBlock(block);

            if (!(block instanceof IModeled)) {
                if (itemBlock instanceof IModeled)
                    handleItemModel(itemBlock);

                return;
            }

            List<String> variants = Lists.newArrayList();
            ((IModeled) block).getVariants(variants);

            if (block instanceof IModeled.Advanced) {
                for (String variant : variants)
                    ModelLoader.registerItemVariants(Item.getItemFromBlock(block), new ModelResourceLocation(block.getRegistryName(), variant));

                ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(block), new MeshDefinitionWrapper((IModeled.Advanced) block));
                return;
            }

            for (int i = 0; i < variants.size(); i++)
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i, new ModelResourceLocation(block.getRegistryName(), variants.get(i)));
        }

        public static void handleItemModel(Item item) {
            if (!(item instanceof IModeled))
                return;

            List<String> variants = Lists.newArrayList();
            ((IModeled) item).getVariants(variants);
            ResourceLocation stateLoc = new ResourceLocation(item.getRegistryName().getResourceDomain(), "item/" + item.getRegistryName().getResourcePath());

            if (item instanceof IModeled.Advanced) {
                for (String variant : variants)
                    ModelLoader.registerItemVariants(item, new ModelResourceLocation(stateLoc, variant));

                ModelLoader.setCustomMeshDefinition(item, new MeshDefinitionWrapper((IModeled.Advanced) item));
                return;
            }

            for (int i = 0; i < variants.size(); i++)
                ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(stateLoc, variants.get(i)));
        }
    }
}
