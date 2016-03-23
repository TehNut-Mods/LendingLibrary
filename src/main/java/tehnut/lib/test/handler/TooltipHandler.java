package tehnut.lib.test.handler;

import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tehnut.lib.annot.Handler;
import tehnut.lib.annot.Used;
import tehnut.lib.test.LendingLibraryTest;

@Handler(client = true)
@Used
public class TooltipHandler {

    @SubscribeEvent
    @Used
    public void onTooltip(ItemTooltipEvent event) {
        event.toolTip.add(LendingLibraryTest.MODID);
    }
}
