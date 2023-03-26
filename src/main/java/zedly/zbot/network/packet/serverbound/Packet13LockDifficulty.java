package   zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.ExtendedDataOutputStream;
/**
* Must have at least op level 2 to use.  Appears to only be used on singleplayer; the difficulty buttons are still disabled in multiplayer.
*/



/**
* Must have at least op level 2 to use.  Appears to only be used on singleplayer; the difficulty buttons are still disabled in multiplayer.
*/

public class Packet13LockDifficulty implements ServerBoundPacket {
    private final boolean locked;


    public Packet13LockDifficulty(boolean locked) {
        this.locked = locked;
    }

    @Override
    public int opCode() {
        return 0x13;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeBoolean(locked);
    }
}
