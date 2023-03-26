package    zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import net.minecraft.server.NBTBase;
import zedly.zbot.GameContext;
import zedly.zbot.PotionEffect;
import zedly.zbot.entity.CraftLivingEntity;
import zedly.zbot.entity.Entity;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* */


/**
* */

public class Packet65EntityEffect implements ClientBoundPacket {
    private int entityID;
    private int effectID;  // See <span class="plainlinks"><a rel="nofollow" class="external text" href="https://minecraft.fandom.com/wiki/Status_effect%23Effect_IDs">this table</a></span>.
    private int amplifier;  // Notchian client displays effect level as Amplifier + 1.
    private int duration;  // Duration in ticks.
    private int flags;  // Bit field, see below.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        effectID = dis.readVarInt();
        amplifier = dis.readByte();
        duration = dis.readVarInt();
        flags = dis.readByte();
    }

}
