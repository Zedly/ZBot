package   zedly.zbot.network.packet.clientbound;

import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.PotionEffect;
import zedly.zbot.entity.CraftLivingEntity;
import zedly.zbot.entity.Entity;

/**
 *
 */
/**
 *
 */

/**
* */


/**
* */

public class Packet3FRemoveEntityEffect implements ClientBoundPacket {
    private int entityID;
    private int effectID;  // See <span class="plainlinks"><a rel="nofollow" class="external text" href="https://minecraft.fandom.com/wiki/Status_effect%23Effect_IDs">this table</a></span>.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        effectID = dis.readVarInt();
    }

}
