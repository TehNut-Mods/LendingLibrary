package tehnut.lib.mc.tile.sync.adapters;

import net.minecraft.nbt.NBTTagCompound;

public class NBTAdapterFloat implements INBTTypeAdapter<Float> {

    public static final INBTTypeAdapter<Float> INSTANCE = new NBTAdapterFloat();

    @Override
    public void write(NBTTagCompound compound, Float value, String fieldName) {
        compound.setFloat(fieldName, value);
    }

    @Override
    public Float read(NBTTagCompound compound, String fieldName) {
        return compound.getFloat(fieldName);
    }
}
