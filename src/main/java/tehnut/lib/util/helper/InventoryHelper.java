package tehnut.lib.util.helper;

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

import javax.annotation.Nullable;

public class InventoryHelper {

    public static IItemHandler getItemHandler(TileEntity tile, EnumFacing side) {
        IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);

        if (handler == null) {
            if (side != null && tile instanceof ISidedInventory)
                handler = new SidedInvWrapper((ISidedInventory) tile, side);
            else if (tile instanceof IInventory)
                handler = new InvWrapper((IInventory) tile);
        }

        return handler;
    }

    public static ItemStack insertStack(TileEntity tile, ItemStack stack) {
        return insertStack(tile, stack, null);
    }

    public static ItemStack insertStack(TileEntity tile, ItemStack stack, EnumFacing facing) {
        IItemHandler handler = getItemHandler(tile, facing);
        return insertStack(handler, stack);
    }

    public static ItemStack insertStack(IItemHandler itemHandler, ItemStack stack) {
        ItemStack copy = stack.copy();
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            copy = itemHandler.insertItem(i, copy, false);
            if (copy == null)
                return null;
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
        return world.spawnEntityInWorld(entityItem);
    }
}
