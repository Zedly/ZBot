package     zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.Location;
import net.minecraft.server.NBTBase;
import net.minecraft.server.NBTTagCompound;
import zedly.zbot.GameContext;
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

public class Packet08BlockEntityData implements ClientBoundPacket {
    private Location location;
    private int type;  // The type of the block entity
    private NBTBase nBTData;  // Data to set.  May be a TAG_END (0), in which case the block entity at the given location is removed (though this is not required since the client will remove the block entity automatically on chunk unload or block removal).


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        location = dis.readPosition();
        type = dis.readVarInt();
        nBTData = dis.readNBT();
    }

    @Override
    public void process(GameContext context) {
        context.getSelf().getEnvironment().setTileAt(location, (NBTTagCompound) nBTData);    }

}
