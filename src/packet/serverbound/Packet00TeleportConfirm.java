package  zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
//Play, Serverbound


/**
* Sent by client as confirmation of <a href="#Player_Position_And_Look_.28clientbound.29">Player Position And Look</a>.
*/

public class Packet00TeleportConfirm implements ServerBoundPacket {
    private final int teleportID;  // The ID given by the <a href="#Player_Position_And_Look_.28clientbound.29">Player Position And Look</a> packet


    public Packet00TeleportConfirm(int teleportID) {
        this.teleportID = teleportID;
    }

    @Override
    public int opCode() {
        return 0x00;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(teleportID);
    }
}
