package tehnut.lib.mc.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class InventoryHelper {

    @Nullable
    public static IItemHandler getItemHandler(@Nullable TileEntity tile, @Nullable EnumFacing side) {
        if (tile == null)
            return null;

        IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);

        if (handler == null) {
            if (side != null && tile instanceof ISidedInventory)
                handler = new SidedInvWrapper((ISidedInventory) tile, side);
            else if (tile instanceof IInventory)
                handler = new InvWrapper((IInventory) tile);
        }

        return handler;
    }

    @Nullable
    public static IItemHandler getItemHandler(World world, BlockPos pos, @Nullable EnumFacing side) {
        return getItemHandler(world.getTileEntity(pos), side);
    }

    @Nonnull
    public static ItemStack insertStack(World world, BlockPos pos, @Nullable EnumFacing facing, ItemStack stack) {
        return insertStack(world.getTileEntity(pos), stack, facing);
    }

    @Nonnull
    public static ItemStack insertStack(@Nullable TileEntity tile, ItemStack stack, @Nullable EnumFacing facing) {
        if (tile == null)
            return stack;

        IItemHandler handler = getItemHandler(tile, facing);
        return insertStack(handler, stack);
    }

    @Nonnull
    public static ItemStack insertStack(@Nullable IItemHandler itemHandler, ItemStack stack) {
        if (itemHandler == null)
            return stack;

        ItemStack copy = stack.copy();
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            copy = itemHandler.insertItem(i, copy, false);
            if (copy.isEmpty())
                return ItemStack.EMPTY;
        }

        return copy;
    }

    public static boolean dropRemainderAtBlock(World world, BlockPos pos, @Nullable EnumFacing pushDirection, ItemStack stack) {
        if (world.isRemote)
            return false;

        EntityItem entityItem = new EntityItem(world);
        BlockPos spawnPos = new BlockPos(pos);
        double velocity = 0.15D;
        if (pushDirection != null) {
            spawnPos.offset(pushDirection);

            switch (pushDirection) {
                case DOWN:
                    entityItem.motionY = -velocity;
                    entityItem.setPosition(spawnPos.getX() + 0.5D, spawnPos.getY() - 1.0D, spawnPos.getZ() + 0.5D);
                    break;
                case UP:
                    entityItem.motionY = velocity;
                    entityItem.setPosition(spawnPos.getX() + 0.5D, spawnPos.getY() + 1.0D, spawnPos.getZ() + 0.5D);
                    break;
                case NORTH:
                    entityItem.motionZ = -velocity;
                    entityItem.setPosition(spawnPos.getX() + 0.5D, spawnPos.getY() + 0.5D, spawnPos.getZ() - 1.0D);
                    break;
                case SOUTH:
                    entityItem.motionZ = velocity;
                    entityItem.setPosition(spawnPos.getX() + 0.5D, spawnPos.getY() + 0.5D, spawnPos.getZ() + 1.0D);
                    break;
                case WEST:
                    entityItem.motionX = -velocity;
                    entityItem.setPosition(spawnPos.getX() - 1.0D, spawnPos.getY() + 0.5D, spawnPos.getZ() + 0.5D);
                    break;
                case EAST:
                    entityItem.motionX = velocity;
                    entityItem.setPosition(spawnPos.getX() + 1.0D, spawnPos.getY() + 0.5D, spawnPos.getZ() + 0.5D);
                    break;
            }
        }

        entityItem.setEntityItemStack(stack);
        return world.spawnEntity(entityItem);
    }
}
