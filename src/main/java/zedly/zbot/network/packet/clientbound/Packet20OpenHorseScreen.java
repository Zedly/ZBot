package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;


/**
* This packet is used exclusively for opening the horse GUI. <a href="#Open_Screen">Open Screen</a> is used for all other GUIs.  The client will not open the inventory if the Entity ID does not point to an horse-like animal.
*/

public class Packet20OpenHorseScreen implements ClientBoundPacket {
    private int windowID;
    private int slotcount;
    private int entityID;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        windowID = dis.readUnsignedByte();
        slotcount = dis.readVarInt();
        entityID = dis.readInt();
    }

}
