package tehnut.lib.mc.tile.sync;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;
import tehnut.lib.forge.util.helper.ReflectionHelper;
import tehnut.lib.mc.tile.sync.adapters.*;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

public class TileSyncBase extends TileEntity {

    private static final Map<Class, INBTTypeAdapter<?>> ADAPTERS = Maps.newHashMap();
    private static final Map<Class, Class> TYPE_MAPPING = Maps.newHashMap();

    private static final Predicate<Field> FIELD_PREDICATE = new Predicate<Field>() {
        @Override
        public boolean apply(@Nullable Field input) {
            return input != null && !Modifier.isFinal(input.getModifiers()) && !Modifier.isStatic(input.getModifiers());
        }
    };

    @Override
    public final void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        List<Field> fields = Lists.newArrayList();
        ReflectionHelper.getAllFields(fields, getClass(), FIELD_PREDICATE);
        ReflectionHelper.permitFields(fields);

        for (Field field : fields) {
            if (field.isAnnotationPresent(NBT.class)) {
                INBTTypeAdapter typeAdapter = getAdapter(field.getType());
                if (typeAdapter == null)
                    throw new RuntimeException("No INBTTypeAdapter registered for annotated field of type " + field.getType().getCanonicalName());

                Object value = typeAdapter.read(tag, field.getName());
                try {
                    field.set(this, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public final NBTTagCompound writeToNBT(NBTTagCompound tag) {
        return serialize(tag, false);
    }

    private NBTTagCompound serialize(NBTTagCompound tag, boolean sync) {
        super.writeToNBT(tag);

        List<Field> fields = Lists.newArrayList();
        ReflectionHelper.getAllFields(fields, getClass(), FIELD_PREDICATE);
        ReflectionHelper.permitFields(fields);

        for (Field field : fields) {
            if (field.isAnnotationPresent(NBT.class)) {
                NBT nbt = field.getAnnotation(NBT.class);
                if (sync && !nbt.sync())
                    continue;

                INBTTypeAdapter typeAdapter = getAdapter(field.getType());
                if (typeAdapter == null)
                    throw new RuntimeException("No INBTTypeAdapter registered for annotated field of type " + field.getType().getCanonicalName());

                try {
                    Object value = field.get(this);
                    typeAdapter.write(tag, value, field.getName());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return tag;
    }

    public void notifyUpdate() {
        getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
    }

    // Data syncing

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public final SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), -999, serialize(new NBTTagCompound(), true));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public final NBTTagCompound getUpdateTag() {
        return serialize(new NBTTagCompound(), true);
    }

    @Override
    public final void handleUpdateTag(NBTTagCompound tag) {
        readFromNBT(tag);
    }

    // Adapters

    static {
        addAdapter(boolean.class, NBTAdapterBoolean.INSTANCE);
        addAdapter(Boolean.class, NBTAdapterBoolean.INSTANCE);
        addAdapter(String.class, NBTAdapterString.INSTANCE);
        addAdapter(int.class, NBTAdapterInteger.INSTANCE);
        addAdapter(Integer.class, NBTAdapterInteger.INSTANCE);
        addAdapter(long.class, NBTAdapterLong.INSTANCE);
        addAdapter(Long.class, NBTAdapterLong.INSTANCE);
        addAdapter(float.class, NBTAdapterFloat.INSTANCE);
        addAdapter(Float.class, NBTAdapterFloat.INSTANCE);
        addAdapter(double.class, NBTAdapterDouble.INSTANCE);
        addAdapter(Double.class, NBTAdapterDouble.INSTANCE);
        addAdapter(Enum.class, NBTAdapterEnum.INSTANCE);
        addAdapter(FluidTank.class, NBTAdapterFluidTank.INSTANCE);
        addAdapter(ItemStackHandler.class, NBTAdapterItemHandler.INSTANCE);
    }

    public static INBTTypeAdapter getAdapter(Class<?> type) {
        if (TYPE_MAPPING.containsKey(type))
            type = TYPE_MAPPING.get(type);

        if (type.isEnum())
            return ADAPTERS.get(Enum.class);

        return ADAPTERS.get(type);
    }

    public static <T> void addAdapter(Class<T> type, INBTTypeAdapter<T> adapter) {
        ADAPTERS.put(type, adapter);
    }

    public static void addTypeMapping(Class from, Class to) {
        TYPE_MAPPING.put(from, to);
    }
}
