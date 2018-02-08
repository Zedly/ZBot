package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* This packet is sent when an entity has been <span class="plainlinks"><a rel="nofollow" class="external text" href="http://minecraft.gamepedia.com/Lead">leashed</a></span> to another entity.
*/

public class Packet3DAttachEntity implements ClientBoundPacket {
    private int attachedEntityID;  // Attached entity's EID
    private int holdingEntityID;  // ID of the entity holding the lead. Set to -1 to detach.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        attachedEntityID = dis.readInt();
        holdingEntityID = dis.readInt();
    }

}
