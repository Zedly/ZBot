package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.event.SoundEffectEvent;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* This packet is used to play a number of hardcoded sound events. For custom sounds, use <a href="#Named_Sound_Effect">Named Sound Effect</a>.
*/

public class Packet52StopSound implements ClientBoundPacket {

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        byte flags = dis.readByte();
        if((flags & 0x1) != 0) {
            int source = dis.readVarInt();
        }
        if((flags & 0x2) != 0) {
            String sound = dis.readString();
        }        
    }
}