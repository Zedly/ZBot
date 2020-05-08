package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
/**
 * This packet is used to inform the client that part of a GUI window should be
 * updated.
 */
public class Packet16WindowProperty implements ClientBoundPacket {

    private int windowID;
    private int property;  // The property to be updated, see below
    private int value;  // The new value for the property, see below

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        windowID = dis.readUnsignedByte();
        property = dis.readShort();
        value = dis.readShort();
    }

    @Override
    public void process(GameContext context) {
        if (context.getSelf().getInventory().windowId() == windowID) {
            context.getSelf().getInventory().setProperty(property, value);
        }
    }

}
