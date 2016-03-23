package tehnut.lib.util.helper;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tehnut.lib.LendingLibrary;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class BlockHelper {

    public static Map<Class<? extends Block>, String> blockClassToName = new HashMap<Class<? extends Block>, String>();

    @Nullable
    public static Block getBlock(String name) {
        return GameRegistry.findBlock(LendingLibrary.getMODID(), name);
    }

    @Nullable
    public static Block getBlock(Class<? extends Block> blockClass) {
        return getBlock(blockClassToName.get(blockClass));
    }

    @Nullable
    public static String getName(Class<? extends Block> blockClass) {
        return blockClassToName.get(blockClass);
    }
}
