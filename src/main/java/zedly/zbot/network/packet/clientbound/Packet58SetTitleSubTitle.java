package      zedly.zbot.network.packet.clientbound;

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


/**
* */

public class Packet58SetTitleSubTitle implements ClientBoundPacket {
    private String subtitleText;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        subtitleText = dis.readString();
    }

}
