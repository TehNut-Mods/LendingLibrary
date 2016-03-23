package tehnut.lib.util.helper;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tehnut.lib.LendingLibrary;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ItemHelper {

    public static Map<Class<? extends Item>, String> itemClassToName = new HashMap<Class<? extends Item>, String>();

    @Nullable
    public static Item getItem(String name) {
        return GameRegistry.findItem(LendingLibrary.getMODID(), name);
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
