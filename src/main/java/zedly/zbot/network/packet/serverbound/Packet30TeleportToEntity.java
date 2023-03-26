package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import java.util.UUID;
import zedly.zbot.network.ExtendedDataOutputStream;
/**
* Teleports the player to the given entity.  The player must be in spectator mode.
*/

public class Packet30TeleportToEntity implements ServerBoundPacket {
    private final UUID targetPlayer;  // UUID of the player to teleport to (can also be an entity UUID).


    public Packet30TeleportToEntity(UUID targetPlayer) {
        this.targetPlayer = targetPlayer;
    }

    @Override
    public int opCode() {
        return 0x30;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeUUID(targetPlayer);
    }
}
