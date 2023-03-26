package zedly.zbot.network.packet.clientbound;

import zedly.zbot.inventory.ItemStack;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 */
public class Packet50EntityEquipment implements ClientBoundPacket {

    private int entityID;  // Entity's EID
    private ArrayList<Pair<Integer, ItemStack>> items = new ArrayList<>();

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        int slot;
        do {
            slot = dis.readByte();
            ItemStack item = dis.readSlot();
            items.add(Pair.of(slot & 0x7F, item));
        } while ((slot & 0x80) != 0);
    }
}
