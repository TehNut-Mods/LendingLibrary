package tehnut.lib.json.serialization;

import com.google.gson.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import tehnut.lib.LendingLibrary;
import tehnut.lib.json.JsonHelper;

import java.lang.reflect.Type;

public class SerializerItemStack extends SerializerBase<ItemStack> {

    public static final String NAME = "name";
    public static final String AMOUNT = "amount";
    public static final String META = "meta";
    public static final String NBT = "nbt";

    private final boolean nbt;

    public SerializerItemStack(boolean nbt) {
        this.nbt = nbt;
    }

    public SerializerItemStack() {
        this(true);
    }

    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ResourceLocation name = new ResourceLocation(JsonHelper.getString(json, NAME, "minecraft:air"));
        int amount = MathHelper.clamp_int(JsonHelper.getInteger(json, AMOUNT, 1), 1, 64);
        int meta = JsonHelper.getInteger(json, META, 0);
        NBTTagCompound tagCompound = null;
        try {
            if (nbt) {
                String nbtJson = JsonHelper.getString(json, NBT, "");
                if (!nbtJson.startsWith("{"))
                    nbtJson = "{" + nbtJson;
                if (!nbtJson.endsWith("}"))
                    nbtJson = nbtJson + "}";
                tagCompound = JsonToNBT.getTagFromJson(nbtJson);
            }
        } catch (NBTException e) {
            LendingLibrary.getLogger().error("Error handling NBT string for {}. Is it formatted correctly?", name);
        }

        ItemStack ret = new ItemStack(ForgeRegistries.ITEMS.getValue(name), amount, meta);
        if (nbt && tagCompound != null)
            ret.setTagCompound(tagCompound);
        return ret;
    }

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(NAME, src.getItem().getRegistryName().toString());
        jsonObject.addProperty(AMOUNT, src.func_190916_E());
        jsonObject.addProperty(META, src.getItemDamage());
        if (nbt && src.getTagCompound() != null)
            jsonObject.addProperty(NBT, src.getTagCompound().toString());
        return jsonObject;
    }

    @Override
    public Class<?> getType() {
        return ItemStack.class;
    }
}
