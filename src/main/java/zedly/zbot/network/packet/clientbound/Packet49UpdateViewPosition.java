/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/*
 * Updates the client's location. This is used to determine what chunks should remain loaded and if a chunk load should be ignored; chunks outside of the view distance may be unloaded.
 * Sent whenever the player moves across a chunk border horizontally, and also (according to testing) for any integer change in the vertical axis, even if it doesn't go across a chunk section border.
 */
public class Packet49UpdateViewPosition implements ClientBoundPacket {
    private int chunkX;  // Chunk X coordinate of the player's position.
    private int chunkZ;  // Chunk Z coordinate of the player's position.

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        chunkX = dis.readVarInt();
        chunkZ = dis.readVarInt();
    }

}