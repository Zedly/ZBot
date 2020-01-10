package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import java.util.UUID;
import zedly.zbot.Location;
import zedly.zbot.GameContext;
import zedly.zbot.entity.CraftEntity;
import zedly.zbot.entity.CraftUnknown;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* This packet shows location, name, and type of painting.
*/

public class Packet04SpawnPainting implements ClientBoundPacket {
    private int entityID;
    private UUID entityUUID;
    private String title;  // Name of the painting. Max length 13
    private Location location;  // Center coordinates (see below)
    private int direction;  // Direction the painting faces (North = 2, South = 0, West = 1, East = 3)


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        entityUUID = dis.readUUID();
        title = dis.readString();
        location = dis.readPosition();
        direction = dis.readByte();
    }

    @Override
    public void process(GameContext context) {
        CraftEntity ent = context.getSelf().getEnvironment().spawnEntity(CraftUnknown.class, entityID, location);
    }

}
