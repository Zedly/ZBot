package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.event.RecipeResponseEvent;
import zedly.zbot.network.ExtendedDataInputStream;
/**
* Response to the serverbound packet (<a href="#Craft_Recipe_Request">Craft Recipe Request</a>), with the same recipe ID.  Appears to be used to notify the UI.
*/


/**
* Response to the serverbound packet (<a href="#Craft_Recipe_Request">Craft Recipe Request</a>), with the same recipe ID.  Appears to be used to notify the UI.
*/

public class Packet30CraftRecipeResponse implements ClientBoundPacket {
    private int windowID;
    private String recipe;  // A recipe ID


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        windowID = dis.readByte();
        recipe = dis.readString();
    }
    
    @Override
    public void process(GameContext context) {
        context.getEventDispatcher().dispatchEvent(new RecipeResponseEvent(windowID, recipe));
    }

}
