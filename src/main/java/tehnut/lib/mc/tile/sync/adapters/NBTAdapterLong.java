package tehnut.lib.mc.tile.sync.adapters;

import net.minecraft.nbt.NBTTagCompound;

public class NBTAdapterLong implements INBTTypeAdapter<Long> {

    public static final INBTTypeAdapter<Long> INSTANCE = new NBTAdapterLong();

    @Override
    public void write(NBTTagCompound compound, Long value, String fieldName) {
        compound.setLong(fieldName, value);
    }

    @Override
    public Long read(NBTTagCompound compound, String fieldName) {
        return compound.getLong(fieldName);
    }
}
