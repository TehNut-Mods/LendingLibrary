package tehnut.lib.mc.tile.sync.adapters;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidTank;

public class NBTAdapterFluidTank implements INBTTypeAdapter<FluidTank> {

    public static final INBTTypeAdapter<FluidTank> INSTANCE = new NBTAdapterFluidTank();

    @Override
    public void write(NBTTagCompound compound, FluidTank value, String fieldName) {
        NBTTagCompound tank = new NBTTagCompound();
        value.writeToNBT(tank);
        tank.setInteger("capacity", value.getCapacity());
        compound.setTag(fieldName, tank);
    }

    @Override
    public FluidTank read(NBTTagCompound compound, String fieldName) {
        NBTTagCompound tankTag = compound.getCompoundTag(fieldName);
        FluidTank tank = new FluidTank(tankTag.getInteger("capacity"));
        tank.readFromNBT(tankTag);
        return tank;
    }
}
