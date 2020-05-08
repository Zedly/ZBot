package   zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.entity.Entity;
import zedly.zbot.entity.Item;
import zedly.zbot.event.entity.EntityItemPickupEvent;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* Sent by the server when someone picks up an item lying on the ground — its sole purpose appears to be the animation of the item flying towards you. It doesn't destroy the entity in the client memory, and it doesn't add it to your inventory. The server only checks for items to be picked up after each <a href="#Player_Position">Player Position</a> (and <a href="#Player_Position_And_Look">Player Position And Look</a>) packet sent by the client.
*/


/**
* Sent by the server when someone picks up an item lying on the ground — its sole purpose appears to be the animation of the item flying towards you. It doesn't destroy the entity in the client memory, and it doesn't add it to your inventory. The server only checks for items to be picked up after each <a href="#Player_Position">Player Position</a> (and <a href="#Player_Position_And_Look">Player Position And Look</a>) packet sent by the client. The collector entity can be any entity; it does not have to be a player. The collected entity also can be any entity, but the Notchian server only uses this for items, experience orbs, and the different varieties of arrows.
*/

public class Packet55CollectItem implements ClientBoundPacket {
    private int collectedEntityID;
    private int collectorEntityID;
    private int pickupItemCount;  // Seems to be 1 for XP orbs, otherwise the number of items in the stack.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        collectedEntityID = dis.readVarInt();
        collectorEntityID = dis.readVarInt();
        pickupItemCount = dis.readVarInt();
    }

}
