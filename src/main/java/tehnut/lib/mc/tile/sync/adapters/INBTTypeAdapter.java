package tehnut.lib.mc.tile.sync.adapters;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTTypeAdapter<T> {

    void write(NBTTagCompound compound, T value, String fieldName);

    T read(NBTTagCompound compound, String fieldName);
}
