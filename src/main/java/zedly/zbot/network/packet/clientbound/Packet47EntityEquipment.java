package    zedly.zbot.network.packet.clientbound;

import zedly.zbot.inventory.ItemStack;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;


/**
* */


/**
* */


/**
* */

public class Packet47EntityEquipment implements ClientBoundPacket {
    private int entityID;  // Entity's EID
    private int slot;  // Equipment slot. 0: main hand, 1: off hand, 2–5: armor slot (2: boots, 3: leggings, 4: chestplate, 5: helmet)
    private ItemStack item;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        slot = dis.readVarInt();
        item = dis.readSlot();
    }

}
