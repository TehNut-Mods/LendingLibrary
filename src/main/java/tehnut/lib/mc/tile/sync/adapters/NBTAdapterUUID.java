package tehnut.lib.mc.tile.sync.adapters;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;

import java.util.UUID;

public class NBTAdapterUUID implements INBTTypeAdapter<UUID> {

    public static final INBTTypeAdapter<UUID> INSTANCE = new NBTAdapterUUID();

    @Override
    public void write(NBTTagCompound compound, UUID value, String fieldName) {
        compound.setTag(fieldName, NBTUtil.createUUIDTag(value));
    }

    @Override
    public UUID read(NBTTagCompound compound, String fieldName) {
        return NBTUtil.getUUIDFromTag(compound.getCompoundTag(fieldName));
    }
}
