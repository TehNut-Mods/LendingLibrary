package tehnut.lib.mc.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.common.registry.RegistryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import tehnut.lib.mc.block.BlockEnum;
import tehnut.lib.mc.block.item.ItemBlockEnum;
import tehnut.lib.mc.model.ModelHandler;

public class RegistryHelper {

    public static <T extends IForgeRegistryEntry<T>> FMLControlledNamespacedRegistry<T> newRegistry(Class<T> type, String id, int min, int max) {
        RegistryBuilder<T> registryBuilder = new RegistryBuilder<T>();
        registryBuilder.setType(type);
        registryBuilder.setName(new ResourceLocation(Loader.instance().activeModContainer().getModId(), id));
        registryBuilder.setIDRange(min, max);
        return (FMLControlledNamespacedRegistry<T>) registryBuilder.create();
    }

    @SuppressWarnings({"MethodCallSideOnly", "unchecked"})
    public static void register(Block block, String name) {
        GameRegistry.register(block.setRegistryName(name));
        if (block instanceof BlockEnum)
            GameRegistry.register(new ItemBlockEnum((BlockEnum) block).setRegistryName(name));
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
            ModelHandler.Client.handleBlockModel(block);
    }

    @SuppressWarnings({"MethodCallSideOnly"})
    public static void register(Item item, String name) {
        GameRegistry.register(item.setRegistryName(name));
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
            ModelHandler.Client.handleItemModel(item);
    }
}
