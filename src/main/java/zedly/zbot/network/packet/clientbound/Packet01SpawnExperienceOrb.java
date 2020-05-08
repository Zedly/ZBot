package    zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.Location;
import zedly.zbot.entity.CraftEntity;
import zedly.zbot.entity.CraftExperienceOrb;
import zedly.zbot.entity.CraftUnknown;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* Spawns one or more experience orbs.
*/

public class Packet01SpawnExperienceOrb implements ClientBoundPacket {
    private int entityID;
    private double x;
    private double y;
    private double z;
    private int count;  // The amount of experience this orb will reward once collected


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        x = dis.readDouble();
        y = dis.readDouble();
        z = dis.readDouble();
        count = dis.readShort();
    }

    @Override
    public void process(GameContext context) {
        CraftExperienceOrb xpOrb = (CraftExperienceOrb) context.getSelf().getEnvironment().spawnEntity(CraftExperienceOrb.class, entityID, new Location(x, y, z));
        xpOrb.setExperienceCount(count);
    }

}
