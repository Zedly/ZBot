package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* */

public class Packet4FEntityEffect implements ClientBoundPacket {
    private int entityID;
    private int effectID;  // See <span class="plainlinks"><a rel="nofollow" class="external text" href="http://minecraft.gamepedia.com/Status_effect%23List_of_effects">this table</a></span>
    private int amplifier;  // Notchian client displays effect level as Amplifier + 1
    private int duration;  // Seconds
    private int flags;  // Bit field, see below.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        effectID = dis.readByte();
        amplifier = dis.readByte();
        duration = dis.readVarInt();
        flags = dis.readByte();
    }

}
