package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
/**
* */


/**
* */

public class Packet57UpdateSimulationDistance implements ClientBoundPacket {
    private int simulationDistance;  // The distance that the client will process specific things, such as entities.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        simulationDistance = dis.readVarInt();
    }

}
