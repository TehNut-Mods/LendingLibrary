package tehnut.lib.mc.tile.sync.adapters;

import net.minecraft.nbt.NBTTagCompound;

public class NBTAdapterDouble implements INBTTypeAdapter<Double> {

    public static final INBTTypeAdapter<Double> INSTANCE = new NBTAdapterDouble();

    @Override
    public void write(NBTTagCompound compound, Double value, String fieldName) {
        compound.setDouble(fieldName, value);
    }

    @Override
    public Double read(NBTTagCompound compound, String fieldName) {
        return compound.getDouble(fieldName);
    }
}
