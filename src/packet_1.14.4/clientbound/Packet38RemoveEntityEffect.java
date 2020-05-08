package zedly.zbot.network.packet.clientbound;

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
public class Packet38RemoveEntityEffect implements ClientBoundPacket {

    private int entityID;
    private int effectID;  // See <span class="plainlinks"><a rel="nofollow" class="external text" href="http://minecraft.gamepedia.com/Status_effect%23List_of_effects">this table</a></span>

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        effectID = dis.readByte();
    }

    public void process(GameContext context) {
        Entity ent = entityID == context.getSelf().getEntityId() ? context.getSelf() : context.getSelf().getEnvironment().getEntityById(entityID);
        if (!(ent instanceof CraftLivingEntity)) {
            System.err.println("Removed Potion Effect for non-LivingEntity " + ent);
            return;
        }
        CraftLivingEntity le = (CraftLivingEntity) ent;
        le.setPotionEffect(PotionEffect.byEffectId(effectID), 0);
    }

}
