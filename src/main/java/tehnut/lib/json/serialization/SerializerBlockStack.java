package tehnut.lib.json.serialization;

import com.google.gson.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import tehnut.lib.json.JsonHelper;
import tehnut.lib.util.BlockStack;

import java.lang.reflect.Type;

public class SerializerBlockStack extends SerializerBase<BlockStack> {

    public static final String NAME = "name";
    public static final String META = "meta";

    @Override
    public BlockStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ResourceLocation name = new ResourceLocation(JsonHelper.getString(json, NAME, "minecraft:air"));
        int meta = JsonHelper.getInteger(json, META, 0);

        return new BlockStack(ForgeRegistries.BLOCKS.getValue(name), meta);
    }

    @Override
    public JsonElement serialize(BlockStack src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(NAME, src.getBlock().getRegistryName().toString());
        jsonObject.addProperty(META, src.getMeta());
        return jsonObject;
    }
}
