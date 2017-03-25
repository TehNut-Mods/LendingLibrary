package tehnut.lib.mc.tile.sync.adapters;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class NBTAdapterItemHandler implements INBTTypeAdapter<ItemStackHandler> {

    public static final INBTTypeAdapter<ItemStackHandler> INSTANCE = new NBTAdapterItemHandler();

    @Override
    public void write(NBTTagCompound compound, ItemStackHandler value, String fieldName) {
        NBTTagCompound inventory = new NBTTagCompound();
        inventory.setInteger("size", value.getSlots());
        inventory.setTag("inventory", value.serializeNBT());
        compound.setTag(fieldName, inventory);
    }

    @Override
    public ItemStackHandler read(NBTTagCompound compound, String fieldName) {
        NBTTagCompound tagCompound = new NBTTagCompound();
        ItemStackHandler stackHandler = new ItemStackHandler(tagCompound.getInteger("size"));
        stackHandler.deserializeNBT(compound.getCompoundTag("inventory"));
        return stackHandler;
    }
}
