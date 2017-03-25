package tehnut.lib.mc.tile.sync.adapters;

import net.minecraft.nbt.NBTTagCompound;

public class NBTAdapterBoolean implements INBTTypeAdapter<Boolean> {

    public static final INBTTypeAdapter<Boolean> INSTANCE = new NBTAdapterBoolean();

    @Override
    public void write(NBTTagCompound compound, Boolean value, String fieldName) {
        compound.setBoolean(fieldName, value);
    }

    @Override
    public Boolean read(NBTTagCompound compound, String fieldName) {
            return compound.getBoolean(fieldName);
    }
}
