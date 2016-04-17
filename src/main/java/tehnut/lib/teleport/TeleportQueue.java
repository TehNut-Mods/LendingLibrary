package tehnut.lib.teleport;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.PriorityQueue;
import java.util.Queue;

public class TeleportQueue {

    public static final TeleportQueue INSTANCE = new TeleportQueue();

    private final Queue<ITeleport> teleportQueue;

    private TeleportQueue() {
        this.teleportQueue = new PriorityQueue<ITeleport>();
    }

    public void addTeleport(ITeleport teleport) {
        teleportQueue.offer(teleport);
    }

    @SubscribeEvent
    public void serverTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;

        while (!teleportQueue.isEmpty())
            teleportQueue.poll().teleport();
    }
}
