package tehnut.lib.util.helper;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import tehnut.lib.LendingLibrary;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ItemHelper {

    public static final Map<Class<? extends Item>, String> itemClassToName = new HashMap<Class<? extends Item>, String>();

    @Nullable
    public static Item getItem(String name) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(LendingLibrary.getMODID(), name));
    }

    @Nullable
    public static Item getItem(Class<? extends Item> itemClass) {
        return getItem(itemClassToName.get(itemClass));
    }

    @Nullable
    public static String getName(Class<? extends Item> itemClass) {
        return itemClassToName.get(itemClass);
    }
}
