package   zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
/**
* Must have at least op level 2 to use.  Appears to only be used on singleplayer; the difficulty buttons are still disabled in multiplayer.
*/


/**
* Must have at least op level 2 to use.  Appears to only be used on singleplayer; the difficulty buttons are still disabled in multiplayer.
*/


/**
* Must have at least op level 2 to use.  Appears to only be used on singleplayer; the difficulty buttons are still disabled in multiplayer.
*/

public class Packet02ChangeDifficulty implements ServerBoundPacket {
    private final int newdifficulty;  // 0: peaceful, 1: easy, 2: normal, 3: hard .


    public Packet02ChangeDifficulty(int newdifficulty) {
        this.newdifficulty = newdifficulty;
    }

    @Override
    public int opCode() {
        return 0x02;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeByte(newdifficulty);
    }
}
