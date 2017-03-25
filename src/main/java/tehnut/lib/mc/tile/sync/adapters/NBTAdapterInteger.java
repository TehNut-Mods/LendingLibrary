package tehnut.lib.mc.tile.sync.adapters;

import net.minecraft.nbt.NBTTagCompound;

public class NBTAdapterInteger implements INBTTypeAdapter<Integer> {

    public static final INBTTypeAdapter<Integer> INSTANCE = new NBTAdapterInteger();

    @Override
    public void write(NBTTagCompound compound, Integer value, String fieldName) {
        compound.setInteger(fieldName, value);
    }

    @Override
    public Integer read(NBTTagCompound compound, String fieldName) {
        return compound.getInteger(fieldName);
    }
}
