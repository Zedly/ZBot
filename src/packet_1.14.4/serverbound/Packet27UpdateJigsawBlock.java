package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataOutputStream;
/**
* Sent when Done is pressed on the <span class="plainlinks"><a rel="nofollow" class="external text" href="http://minecraft.gamepedia.com/Jigsaw_Block">Jigsaw Block</a></span> interface.
*/

public class Packet27UpdateJigsawBlock implements ServerBoundPacket {
    private final Location location;  // Block entity location
    private final String attachmenttype;
    private final String targetpool;
    private final String finalstate;  // "Turns into" on the GUI, <code>final_state</code> in NBT


    public Packet27UpdateJigsawBlock(Location location, String attachmenttype, String targetpool, String finalstate) {
        this.location = location;
        this.attachmenttype = attachmenttype;
        this.targetpool = targetpool;
        this.finalstate = finalstate;
    }

    @Override
    public int opCode() {
        return 0x27;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writePosition(location);
        dos.writeString(attachmenttype);
        dos.writeString(targetpool);
        dos.writeString(finalstate);
    }
}
