package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import zedly.zbot.CraftPlayerProperty;
import zedly.zbot.GameContext;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
/**
 *
 */
public class Packet02LoginSuccess implements ClientBoundPacket {

    private UUID uUID;  // Unlike in other packets, this field contains the UUID as a string with hyphens.
    private String username;
    private final ArrayList<CraftPlayerProperty> properties = new ArrayList<>();

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        uUID = dis.readUUID();
        username = dis.readString();
        int numProperties = dis.readByte();
        for (int i = 0; i < numProperties; i++) {
            String name = dis.readString();
            String value = dis.readString();
            boolean signed = dis.readBoolean();
            if (signed) {
                String signature = dis.readString();
                properties.add(new CraftPlayerProperty(name, value, signed, signature));
            }
            properties.add(new CraftPlayerProperty(name, value, signed, null));
        }
    }

    @Override
    public void process(GameContext context) {
        context.getSelf().setPlayerProperties(properties);
    }
}
