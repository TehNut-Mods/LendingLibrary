package tehnut.lib.json.serialization;

import com.google.gson.*;
import net.minecraft.util.math.BlockPos;
import tehnut.lib.json.JsonHelper;

import java.lang.reflect.Type;

public class SerializerBlockPos extends SerializerBase<BlockPos> {

    public static final String X_COORD = "xCoord";
    public static final String Y_COORD = "yCoord";
    public static final String Z_COORD = "zCoord";

    public static final String X_OFF = "xOff";
    public static final String Y_OFF = "yOff";
    public static final String Z_OFF = "zOff";

    private final boolean offset;

    public SerializerBlockPos(boolean offset) {
        this.offset = offset;
    }

    public SerializerBlockPos() {
        this(false);
    }

    @Override
    public BlockPos deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int x = JsonHelper.getInteger(json, offset ? X_OFF : X_COORD, 0);
        int y = JsonHelper.getInteger(json, offset ? Y_OFF : Y_COORD, 0);
        int z = JsonHelper.getInteger(json, offset ? Z_OFF : Z_COORD, 0);

        return new BlockPos(x, y, z);
    }

    @Override
    public JsonElement serialize(BlockPos src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(offset ? X_OFF : X_COORD, src.getX());
        jsonObject.addProperty(offset ? Y_OFF : Y_COORD, src.getY());
        jsonObject.addProperty(offset ? Z_OFF : Z_COORD, src.getZ());
        return jsonObject;
    }

    @Override
    public Class<?> getType() {
        return BlockPos.class;
    }
}
