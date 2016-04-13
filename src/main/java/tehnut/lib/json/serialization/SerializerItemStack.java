package tehnut.lib.json.serialization;

import com.google.gson.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import tehnut.lib.json.JsonHelper;

import java.lang.reflect.Type;

public class SerializerItemStack implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {

    public static final String NAME = "name";
    public static final String AMOUNT = "amount";
    public static final String META = "meta";
    public static final String NBT = "nbt";

    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ResourceLocation name = new ResourceLocation(JsonHelper.getString(json, NAME, "minecraft:air"));
        int amount = MathHelper.clamp_int(JsonHelper.getInteger(json, AMOUNT, 1), 1, 64);
        int meta = JsonHelper.getInteger(json, META, 0);
        NBTTagCompound tagCompound = null;
        try {
            tagCompound = JsonToNBT.getTagFromJson(JsonHelper.getString(json, NBT, ""));
        } catch (NBTException e) {
            // Pokeball!
        }

        ItemStack ret = new ItemStack(Item.itemRegistry.getObject(name), amount, meta);
        if (tagCompound != null)
            ret.setTagCompound(tagCompound);
        return ret;
    }

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(NAME, src.getItem().getRegistryName().toString());
        jsonObject.addProperty(AMOUNT, src.stackSize);
        jsonObject.addProperty(META, src.getItemDamage());
        if (src.getTagCompound() != null)
            jsonObject.addProperty(NBT, src.getTagCompound().toString());
        return null;
    }
}
