package tehnut.lib.mc.util;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
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
import org.apache.commons.lang3.tuple.Pair;
import tehnut.lib.mc.block.BlockEnum;
import tehnut.lib.mc.block.item.ItemBlockEnum;
import tehnut.lib.mc.model.ModelHandler;

import javax.annotation.Nullable;
import java.util.Map;

public class RegistryHelper {

    private static final Map<Class<? extends IForgeRegistryEntry>, Function<Pair<IForgeRegistryEntry, String>, Void>> REGISTRY_CASES = Maps.newHashMap();

    static {
        REGISTRY_CASES.put(Block.class, new Function<Pair<IForgeRegistryEntry, String>, Void>() {
            @SuppressWarnings({"MethodCallSideOnly", "unchecked"})
            @Nullable
            @Override
            public Void apply(@Nullable Pair<IForgeRegistryEntry, String> input) {
                if (input == null)
                    return null;

                Block block = (Block) input.getLeft();
                GameRegistry.register(block.setRegistryName(input.getRight()));
                if (block instanceof BlockEnum)
                    GameRegistry.register(new ItemBlockEnum((BlockEnum) block).setRegistryName(input.getRight()));
                if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
                    ModelHandler.Client.handleBlockModel(block);
                return null;
            }
        });
        REGISTRY_CASES.put(Item.class, new Function<Pair<IForgeRegistryEntry, String>, Void>() {
            @SuppressWarnings({"MethodCallSideOnly", "unchecked"})
            @Nullable
            @Override
            public Void apply(@Nullable Pair<IForgeRegistryEntry, String> input) {
                Item item = (Item) input.getLeft();
                GameRegistry.register(item.setRegistryName(input.getRight()));
                if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
                    ModelHandler.Client.handleItemModel(item);
                return null;
            }
        });
    }

    public static <T extends IForgeRegistryEntry<T>> FMLControlledNamespacedRegistry<T> newRegistry(Class<T> type, String id, int min, int max) {
        RegistryBuilder<T> registryBuilder = new RegistryBuilder<T>();
        registryBuilder.setType(type);
        registryBuilder.setName(new ResourceLocation(Loader.instance().activeModContainer().getModId(), id));
        registryBuilder.setIDRange(min, max);
        return (FMLControlledNamespacedRegistry<T>) registryBuilder.create();
    }

    public static <T extends IForgeRegistryEntry<T>> T register(T type, String name) {
        boolean reg = false;
        for (Map.Entry<Class<? extends IForgeRegistryEntry>, Function<Pair<IForgeRegistryEntry, String>, Void>> entry : REGISTRY_CASES.entrySet()) {
            if (entry.getKey().isAssignableFrom(type.getClass())) {
                entry.getValue().apply(Pair.of((IForgeRegistryEntry) type, name));
                reg = true;
                break;
            }
        }

        if (!reg)
            GameRegistry.register(type.setRegistryName(new ResourceLocation(Loader.instance().activeModContainer().getModId(), name)));

        return type;
    }
}
