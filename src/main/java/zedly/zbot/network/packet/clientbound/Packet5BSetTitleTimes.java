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

public class Packet5BSetTitleTimes implements ClientBoundPacket {
    private int fadeIn;  // Ticks to spend fading in.
    private int stay;  // Ticks to keep the title displayed.
    private int fadeOut;  // Ticks to spend out, not when to start fading out.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        fadeIn = dis.readInt();
        stay = dis.readInt();
        fadeOut = dis.readInt();
    }

}
