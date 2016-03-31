package tehnut.lib.test.handler;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import tehnut.lib.annot.Handler;
import tehnut.lib.annot.Used;

@Handler
@Used
public class TickHandler {

    private final boolean ENABLED = false;

    @SubscribeEvent
    @Used
    public void onTick(TickEvent.WorldTickEvent event) {
        if (ENABLED && event.world.getTotalWorldTime() % 200 == 0)
            System.out.println("boop");
    }
}
