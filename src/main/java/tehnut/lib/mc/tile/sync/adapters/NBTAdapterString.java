package tehnut.lib.mc.tile.sync.adapters;

import net.minecraft.nbt.NBTTagCompound;

public class NBTAdapterString implements INBTTypeAdapter<String> {

    public static final INBTTypeAdapter<String> INSTANCE = new NBTAdapterString();

    @Override
    public void write(NBTTagCompound compound, String value, String fieldName) {
        compound.setString(fieldName, value);
    }

    @Override
    public String read(NBTTagCompound compound, String fieldName) {
        return compound.getString(fieldName);
    }
}
