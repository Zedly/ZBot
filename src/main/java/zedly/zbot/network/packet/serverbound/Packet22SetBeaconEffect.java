package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
/**
* Changes the effect of the current beacon.
*/

public class Packet22SetBeaconEffect implements ServerBoundPacket {
    private final int primaryEffect;  // A <a rel="nofollow" class="external text" href="http://minecraft.gamepedia.com/Data_values#Potions">Potion ID</a>. (Was a full Integer for the plugin message)
    private final int secondaryEffect;  // A <a rel="nofollow" class="external text" href="http://minecraft.gamepedia.com/Data_values#Potions">Potion ID</a>. (Was a full Integer for the plugin message)


    public Packet22SetBeaconEffect(int primaryEffect, int secondaryEffect) {
        this.primaryEffect = primaryEffect;
        this.secondaryEffect = secondaryEffect;
    }

    @Override
    public int opCode() {
        return 0x22;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(primaryEffect);
        dos.writeVarInt(secondaryEffect);
    }
}
