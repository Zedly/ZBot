package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.Location;
import zedly.zbot.event.SelfTeleportEvent;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.packet.serverbound.Packet00TeleportConfirm;

/**
 *
 * @author Dennis
 */
/**
 * Updates the player's position on the server. This packet will also close the
 * “Downloading Terrain” screen when joining/respawning.
 */

/**
* Updates the player's position on the server. This packet will also close the “Downloading Terrain” screen when joining/respawning.
*/

public class Packet3CSynchronizePlayerPosition implements ClientBoundPacket {
    private double x;  // Absolute or relative position, depending on Flags.
    private double y;  // Absolute or relative position, depending on Flags.
    private double z;  // Absolute or relative position, depending on Flags.
    private double yaw;  // Absolute or relative rotation on the X axis, in degrees.
    private double pitch;  // Absolute or relative rotation on the Y axis, in degrees.
    private int flags;  // Bit field, see below.
    private int teleportID;  // Client should confirm this packet with <a href="#Confirm_Teleportation">Confirm Teleportation</a> containing the same Teleport ID.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        x = dis.readDouble();
        y = dis.readDouble();
        z = dis.readDouble();
        yaw = dis.readFloat();
        pitch = dis.readFloat();
        flags = dis.readByte();
        teleportID = dis.readVarInt();
    }

    @Override
    public void process(GameContext context) {
        context.getUpThread().sendPacket(new Packet00TeleportConfirm(teleportID));
        Location l = new Location(x, y, z, yaw, pitch);
        context.getSelf().moveTo(l);
        context.getMainThread().fireEvent(new SelfTeleportEvent(l, teleportID));    }

}
