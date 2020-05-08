package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
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
 *
 */
/**
 *
 */
public class Packet59EntityEffect implements ClientBoundPacket {

    private int entityID;
    private int effectID;  // See <span class="plainlinks"><a rel="nofollow" class="external text" href="http://minecraft.gamepedia.com/Status_effect%23List_of_effects">this table</a></span>
    private int amplifier;  // Notchian client displays effect level as Amplifier + 1
    private int duration;  // Seconds
    private int flags;  // Bit field, see below.

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        effectID = dis.readByte();
        amplifier = dis.readByte();
        duration = dis.readVarInt();
        flags = dis.readByte();
    }

    @Override
    public void process(GameContext context) {
        Entity ent = entityID == context.getSelf().getEntityId() ? context.getSelf() : context.getSelf().getEnvironment().getEntityById(entityID);
        if (!(ent instanceof CraftLivingEntity)) {
            System.err.println("Set Potion Effect for non-LivingEntity " + ent);
            return;
        }
        CraftLivingEntity le = (CraftLivingEntity) ent;
        le.setPotionEffect(PotionEffect.byEffectId(effectID), amplifier + 1);
    }
}
