package tehnut.lib.mc.json.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Type;

public class SerializerPair<L, R> extends SerializerBase<Pair<L, R>> {

    public static final String LEFT = "left";
    public static final String RIGHT = "right";

    @Override
    public Pair<L, R> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        L left = (L) json.getAsJsonObject().get(LEFT);
        R right = (R) json.getAsJsonObject().get(RIGHT);
        return Pair.of(left, right);
    }

    @Override
    public JsonElement serialize(Pair<L, R> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add(LEFT, context.serialize(src.getLeft(), new TypeToken<L>(){}.getType()));
        jsonObject.add(RIGHT, context.serialize(src.getRight(), new TypeToken<R>(){}.getType()));
        return jsonObject;
    }

    @Override
    public Class<?> getType() {
        return Pair.class;
    }
}
