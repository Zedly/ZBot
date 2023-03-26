package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;

/**
 * Changes the effect of the current beacon.
 */
public class Packet27SetBeaconEffect implements ServerBoundPacket {

    private final int primaryEffect;  // A <a rel="nofollow" class="external text" href="http://minecraft.gamepedia.com/Data_values#Potions">Potion ID</a>. (Was a full Integer for the plugin message)
    private final int secondaryEffect;  // A <a rel="nofollow" class="external text" href="http://minecraft.gamepedia.com/Data_values#Potions">Potion ID</a>. (Was a full Integer for the plugin message)

    public Packet27SetBeaconEffect(int primaryEffect, int secondaryEffect) {
        this.primaryEffect = primaryEffect;
        this.secondaryEffect = secondaryEffect;
    }

    @Override
    public int opCode() {
        return 0x27;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeBoolean(primaryEffect != -1);
        if (primaryEffect != -1) {
            dos.writeVarInt(primaryEffect);
        }
        dos.writeBoolean(secondaryEffect != -1);
        if (secondaryEffect != -1) {
            dos.writeVarInt(secondaryEffect);
        }
    }
}
