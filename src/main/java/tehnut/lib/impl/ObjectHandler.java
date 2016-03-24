package tehnut.lib.impl;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import tehnut.lib.LendingLibrary;
import tehnut.lib.annot.ModBlock;
import tehnut.lib.annot.ModItem;
import tehnut.lib.util.helper.BlockHelper;
import tehnut.lib.util.helper.ItemHelper;

public class ObjectHandler {

    protected static void registerBlocks(Side side) {
        for (ASMDataTable.ASMData data : LendingLibrary.getInstance().getModBlocks()) {
            try {
                Class<?> asmClass = Class.forName(data.getClassName());

                Class<? extends Block> modBlockClass = asmClass.asSubclass(Block.class);
                String name = modBlockClass.getAnnotation(ModBlock.class).name();
                Class<? extends TileEntity> tileClass = modBlockClass.getAnnotation(ModBlock.class).tileEntity();
                Class<? extends ItemBlock> itemBlockClass = modBlockClass.getAnnotation(ModBlock.class).itemBlock();

                Block modBlock = modBlockClass.newInstance();

                GameRegistry.registerBlock(modBlock, itemBlockClass, name);
                if (tileClass != TileEntity.class)
                    GameRegistry.registerTileEntity(tileClass, LendingLibrary.getMODID() + ":" + tileClass.getSimpleName());
                if (side == Side.CLIENT)
                    LendingLibrary.getInstance().getClientHandler().tryHandleBlockModel(modBlock, name);
                BlockHelper.blockClassToName.put(modBlockClass, name);
            } catch (Exception e) {
                LendingLibrary.getLogger().error("Unable to register block for class {}", data.getClassName());
                LendingLibrary.getLogger().error(e.getMessage());
            }
        }
    }

    protected static void registerItems(Side side) {
        for (ASMDataTable.ASMData data : LendingLibrary.getInstance().getModItems()) {
            try {
                Class<?> asmClass = Class.forName(data.getClassName());
                Class<? extends Item> modItemClass = asmClass.asSubclass(Item.class);
                String name = modItemClass.getAnnotation(ModItem.class).name();

                Item modItem = modItemClass.newInstance();

                GameRegistry.registerItem(modItem, name);
                if (side == Side.CLIENT)
                    LendingLibrary.getInstance().getClientHandler().tryHandleItemModel(modItem, name);
                ItemHelper.itemClassToName.put(modItemClass, name);
            } catch (Exception e) {
                LendingLibrary.getLogger().error("Unable to register item for class {}", data.getClassName());
            }
        }
    }
}
