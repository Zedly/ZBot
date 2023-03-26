package   zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.Location;
import zedly.zbot.network.ExtendedDataOutputStream;
/**
* Sent when Done is pressed on the <span class="plainlinks"><a rel="nofollow" class="external text" href="http://minecraft.gamepedia.com/Jigsaw_Block">Jigsaw Block</a></span> interface.
*/



/**
* Sent when Done is pressed on the <span class="plainlinks"><a rel="nofollow" class="external text" href="https://minecraft.fandom.com/wiki/Jigsaw_Block">Jigsaw Block</a></span> interface.
*/

public class Packet29UpdateJigsawBlock implements ServerBoundPacket {
    private final Location location;  // Block entity location
    private final String name;
    private final String target;
    private final String pool;
    private final String finalstate;  // "Turns into" on the GUI, <code>final_state</code> in NBT.
    private final String jointtype;  // <code>rollable</code> if the attached piece can be rotated, else <code>aligned</code>.


    public Packet29UpdateJigsawBlock(Location location, String name, String target, String pool, String finalstate, String jointtype) {
        this.location = location;
        this.name = name;
        this.target = target;
        this.pool = pool;
        this.finalstate = finalstate;
        this.jointtype = jointtype;
    }

    @Override
    public int opCode() {
        return 0x29;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writePosition(location);
        dos.writeString(name);
        dos.writeString(target);
        dos.writeString(pool);
        dos.writeString(finalstate);
        dos.writeString(jointtype);
    }
}
