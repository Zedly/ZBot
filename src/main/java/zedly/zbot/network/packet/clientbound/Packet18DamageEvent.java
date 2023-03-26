package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.Location;
import zedly.zbot.PotionEffect;
import zedly.zbot.entity.CraftLivingEntity;
import zedly.zbot.entity.Entity;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet18DamageEvent implements ClientBoundPacket {

    private int entityID;
    private int sourceTypeID;
    private int sourceCauseID;
    private int sourceDirectID;
    private boolean hasSourcePosition;
    private Location sourceLocation;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        sourceTypeID = dis.readVarInt();
        sourceCauseID = dis.readVarInt();
        sourceDirectID = dis.readVarInt();
        hasSourcePosition = dis.readBoolean();
        if (hasSourcePosition) {
            double x = dis.readDouble();
            double y = dis.readDouble();
            double z = dis.readDouble();
            sourceLocation = new Location(x, y, z);
        }
    }

    @Override
    public void process(GameContext context) {
        Entity ent = entityID == context.getSelf().getEntityId() ? context.getSelf() : context.getSelf().getEnvironment().getEntityById(entityID);
        if (!(ent instanceof CraftLivingEntity)) {
            System.err.println("Entity damage event on non-LivingEntity " + ent);
            return;
        }
    }
}
