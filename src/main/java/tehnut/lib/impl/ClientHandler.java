package tehnut.lib.impl;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.lang3.tuple.Pair;
import tehnut.lib.LendingLibrary;
import tehnut.lib.annot.Handler;
import tehnut.lib.annot.Used;
import tehnut.lib.iface.IMeshProvider;
import tehnut.lib.iface.IVariantProvider;

@Used
public class ClientHandler extends CommonHandler {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        for (ASMDataTable.ASMData data : LendingLibrary.getInstance().getModHandlers()) {
            try {
                Class<?> asmClass = Class.forName(data.getClassName());
                boolean client = asmClass.getAnnotation(Handler.class).client();

                Object handler = asmClass.newInstance();

                if (client)
                    MinecraftForge.EVENT_BUS.register(handler);
            } catch (Exception e) {
                LendingLibrary.getLogger().error("Unable to register event handler for class {}", data.getClassName());
            }
        }
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public void tryHandleBlockModel(Block block, String name) {
        if (block instanceof IVariantProvider) {
            IVariantProvider variantProvider = (IVariantProvider) block;
            for (Pair<Integer, String> variant : variantProvider.getVariants())
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), variant.getLeft(), new ModelResourceLocation(new ResourceLocation(LendingLibrary.getMODID(), name), variant.getRight()));
        }
    }

    @Override
    public void tryHandleItemModel(Item item, String name) {
        if (item instanceof IMeshProvider) {
            IMeshProvider meshProvider = (IMeshProvider) item;
            ModelLoader.setCustomMeshDefinition(item, meshProvider.getMeshDefinition());
            ResourceLocation resourceLocation = meshProvider.getCustomLocation();
            if (resourceLocation == null)
                resourceLocation = new ResourceLocation(LendingLibrary.getMODID(), "item/" + name);
            for (String variant : meshProvider.getVariants())
                ModelLoader.registerItemVariants(item, new ModelResourceLocation(resourceLocation, variant));
        } else if (item instanceof IVariantProvider) {
            IVariantProvider variantProvider = (IVariantProvider) item;
            for (Pair<Integer, String> variant : variantProvider.getVariants())
                ModelLoader.setCustomModelResourceLocation(item, variant.getLeft(), new ModelResourceLocation(new ResourceLocation(LendingLibrary.getMODID(), "item/" + name), variant.getRight()));
        }
    }
}
