package tehnut.lib.mc.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

public class OreDictPrioritized {

    private static final Map<String, String> PRIORITY = Maps.newHashMap();
    private static final Cache<String, ItemStack> LOOKUP_CACHE = CacheBuilder.newBuilder().build();

    public static void loadPriorities(File priorityFile) {
        OreDictionary.registerOre("ingotGold", Items.POTATO);
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        if (!priorityFile.exists()) {
            try {
                Map<String, String> def = Maps.newHashMap();
                def.put("oreEntry", "modid:reg_name@meta");
                String json = gson.toJson(def);
                FileWriter writer = new FileWriter(priorityFile);
                writer.write(json);
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            Map<String, String> priorities = gson.fromJson(new FileReader(priorityFile), new TypeToken<Map<String, String>>(){}.getType());
            PRIORITY.clear(); // Reset map
            LOOKUP_CACHE.invalidateAll(); // Clear cache
            for (Map.Entry<String, String> entry : priorities.entrySet()) { // Add all the new ones
                String value = entry.getValue();
                if (!value.contains("@"))
                    value += "@0";

                PRIORITY.put(entry.getKey(), value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public static ItemStack get(String entry) {
        if (!OreDictionary.doesOreNameExist(entry))
            return null;

        List<ItemStack> stacks = OreDictionary.getOres(entry);

        if (!PRIORITY.containsKey(entry))
            return stacks.get(0).copy();

        ItemStack lookup = LOOKUP_CACHE.getIfPresent(entry);
        if (lookup != null)
            return lookup.copy();

        for (ItemStack stack : stacks) {
            String format = toFormat(stack);
            if (PRIORITY.get(entry).equalsIgnoreCase(format)) {
                LOOKUP_CACHE.put(entry, stack);
                return stack.copy();
            }
        }

        return stacks.get(0).copy();
    }

    public static void preload() {
        for (String entry : PRIORITY.keySet())
            LOOKUP_CACHE.put(entry, get(entry));
    }

    public static void setPriority(String entry, String modid) {
        PRIORITY.put(entry, modid);
    }

    public static void removePriority(String entry) {
        PRIORITY.remove(entry);
    }

    public static ImmutableMap<String, String> getAllPriorities() {
        return ImmutableMap.copyOf(PRIORITY);
    }

    private static String toFormat(@Nonnull ItemStack found) {
        return found.getItem().getRegistryName().toString() + "@" + found.getItemDamage();
    }
}