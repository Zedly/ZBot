package  zedly.zbot.network.packet.clientbound;

import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;


/**
* */

public class Packet33RemoveEntityEffect implements ClientBoundPacket {
    private int entityID;
    private int effectID;  // See <span class="plainlinks"><a rel="nofollow" class="external text" href="http://minecraft.gamepedia.com/Status_effect%23List_of_effects">this table</a></span>


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        effectID = dis.readByte();
    }

}
