package   zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import net.minecraft.server.NBTBase;
import zedly.zbot.network.ExtendedDataInputStream;
/**
* Sent in response to <a href="#Query_Block_NBT">Query Block NBT</a> or <a href="#Query_Entity_NBT">Query Entity NBT</a>.
*/


/**
* Sent in response to <a href="#Query_Block_Entity_Tag">Query Block Entity Tag</a> or <a href="#Query_Entity_Tag">Query Entity Tag</a>.
*/

public class Packet66TagQueryResponse implements ClientBoundPacket {
    private int transactionID;  // Can be compared to the one sent in the original query packet.
    private NBTBase nBT;  // The NBT of the block or entity.  May be a TAG_END (0) in which case no NBT is present.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        transactionID = dis.readVarInt();
        nBT = dis.readNBT();
    }

}
