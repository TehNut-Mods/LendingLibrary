package tehnut.lib.mc.tile.sync.adapters;

import net.minecraft.nbt.NBTTagCompound;

public class NBTAdapterEnum implements INBTTypeAdapter<Enum> {

    public static final INBTTypeAdapter<Enum> INSTANCE = new NBTAdapterEnum();

    @Override
    public void write(NBTTagCompound compound, Enum value, String fieldName) {
        NBTTagCompound enumTag = new NBTTagCompound();
        enumTag.setString("className", value
                .getClass()
                .getName());
        enumTag.setString("value", value.name());

        compound.setTag(fieldName, enumTag);
    }

    @Override
    public Enum read(NBTTagCompound compound, String fieldName) {
        NBTTagCompound enumTag = compound.getCompoundTag(fieldName);
        try {
            Class<? extends Enum> enumClass = (Class<? extends Enum>) Class.forName(enumTag.getString("className"));
            return Enum.valueOf(enumClass, enumTag.getString("value"));
        } catch (Exception e) {
            // Pokeball
        }
        return null;
    }
}
