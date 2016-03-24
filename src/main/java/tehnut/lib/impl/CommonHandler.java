package tehnut.lib.impl;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tehnut.lib.LendingLibrary;
import tehnut.lib.annot.Handler;
import tehnut.lib.iface.IProxy;

public class CommonHandler implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        for (ASMDataTable.ASMData data : LendingLibrary.getInstance().getModHandlers()) {
            try {
                Class<?> asmClass = Class.forName(data.getClassName());
                boolean client = asmClass.getAnnotation(Handler.class).client();

                Object handler = asmClass.newInstance();

                if (!client)
                    MinecraftForge.EVENT_BUS.register(handler);
            } catch (Exception e) {
                LendingLibrary.getLogger().error("Unable to register event handler for class {}", data.getClassName());
            }
        }

        ObjectHandler.registerBlocks(event.getSide());
        ObjectHandler.registerItems(event.getSide());
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    public void tryHandleBlockModel(Block block, String name) {
        // NO-OP
    }

    public void tryHandleItemModel(Item item, String name) {
        // NO-OP
    }
}
