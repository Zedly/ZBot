package    zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataOutputStream;
/**
* Sent when Done is pressed on the <span class="plainlinks"><a rel="nofollow" class="external text" href="http://minecraft.gamepedia.com/Jigsaw_Block">Jigsaw Block</a></span> interface.
*/



/**
* Sent when Generate is pressed on the <span class="plainlinks"><a rel="nofollow" class="external text" href="https://minecraft.fandom.com/wiki/Jigsaw_Block">Jigsaw Block</a></span> interface.
*/


/**
* Sent when Generate is pressed on the <span class="plainlinks"><a rel="nofollow" class="external text" href="https://minecraft.fandom.com/wiki/Jigsaw_Block">Jigsaw Block</a></span> interface.
*/

public class Packet0EGenerateStructure implements ServerBoundPacket {
    private final Location location;  // Block entity location.
    private final int levels;  // Value of the levels slider/max depth to generate.
    private final boolean keepJigsaws;


    public Packet0EGenerateStructure(Location location, int levels, boolean keepJigsaws) {
        this.location = location;
        this.levels = levels;
        this.keepJigsaws = keepJigsaws;
    }

    @Override
    public int opCode() {
        return 0x0E;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writePosition(location);
        dos.writeVarInt(levels);
        dos.writeBoolean(keepJigsaws);
    }
}
