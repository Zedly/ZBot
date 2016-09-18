package zedly.zbot;

import zedly.zbot.event.Event;
import zedly.zbot.network.packet.clientbound.ClientBoundPacket;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ZBotThreadPoolExecutor extends ScheduledThreadPoolExecutor {

    private final GameContext context;

    public ZBotThreadPoolExecutor(GameContext context, int i) {
        super(i);
        this.context = context;
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
    }

    public void processPacket(ClientBoundPacket p) {
        execute(() -> {
            synchronized (context.getSelf()) {
                try {
                    p.process(context);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void fireEvent(Event evt) {
        execute(() -> context.getEventDispatcher().dispatchEvent(evt));
    }
}
