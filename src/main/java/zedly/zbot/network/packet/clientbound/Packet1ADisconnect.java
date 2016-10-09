package zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.StringUtil;
import zedly.zbot.event.SelfKickEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

public class Packet1ADisconnect implements ClientBoundPacket {

    private String reason;
    private String formattedReason;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        reason = dis.readString();
        formattedReason = StringUtil.extract(reason, "\":\"", "\"}");
    }

    @Override
    public void process(GameContext context) {
        System.out.println("Kicked: " + formattedReason);
        context.getMainThread().fireEvent(new SelfKickEvent(reason, formattedReason));
        context.cleanUpConnection();
    }
}
