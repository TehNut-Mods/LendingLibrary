package tehnut.lib.mc.json.serialization;

import com.google.gson.*;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import tehnut.lib.forge.json.serialization.SerializerBase;

import java.lang.reflect.Type;

public class SerializerBlockState extends SerializerBase<IBlockState> {

    @Override
    public IBlockState deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String[] split = json.getAsString().split("\\[");
        split[1] = split[1].substring(0, split[1].lastIndexOf("]")); // Make sure brackets are removed from state

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(split[0]));
        if (block == Blocks.AIR)
            return Blocks.AIR.getDefaultState();

        assert block != null; // getValue is marked as nullable, but it isn't for Blocks
        BlockStateContainer blockState = block.getBlockState();
        IBlockState returnState = blockState.getBaseState();

        // Force our values into the state
        String[] stateValues = split[1].split(","); // Splits up each value
        for (String value : stateValues) {
            String[] valueSplit = value.split("=");
            IProperty property = blockState.getProperty(valueSplit[0]);
            if (property != null)
                returnState = returnState.withProperty(property, (Comparable) property.parseValue(valueSplit[1]).get());
        }

        return returnState;
    }

    @Override
    public JsonElement serialize(IBlockState src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }

    @Override
    public Class<?> getType() {
        return IBlockState.class;
    }
}