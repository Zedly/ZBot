package   zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.Location;
import net.minecraft.server.NBTBase;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* Sets tile entity associated with the block at the given location.
*/


/**
* Sets the block entity associated with the block at the given location.
*/

public class Packet09UpdateBlockEntity implements ClientBoundPacket {
    private Location location;
    private int action;  // The type of update to perform, see below
    private NBTBase nBTData;  // Data to set.  May be a TAG_END (0), in which case the block entity at the given location is removed (though this is not required since the client will remove the block entity automatically on chunk unload or block removal)


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        location = dis.readPosition();
        action = dis.readUnsignedByte();
        nBTData = dis.readNBT();
    }

}
