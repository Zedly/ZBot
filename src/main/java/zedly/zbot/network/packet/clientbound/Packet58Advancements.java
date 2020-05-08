package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import java.util.HashMap;
import zedly.zbot.Advancement;
import zedly.zbot.AdvancementProgress;
import zedly.zbot.network.ExtendedDataInputStream;

public class Packet58Advancements implements ClientBoundPacket {

    private boolean resetClear;
    private int mappingSize;
    private HashMap<String, Advancement> advancementMapping;
    private int listSize;
    private String[] identifiers;
    private int progressSize;
    private HashMap<String, AdvancementProgress> progressMapping;

    
    // Disabled: Off by one on some servers
    public void readPacket0(ExtendedDataInputStream dis, int packetLen) throws IOException {
        resetClear = dis.readBoolean();
        mappingSize = dis.readVarInt();
        advancementMapping = new HashMap<>();
        for (int i = 0; i < mappingSize - 1; i++) {
            System.out.println(i);
            if (i == 164) {
                advancementMapping.put(dis.readString(), dis.readAdvancement());
            } else {
                advancementMapping.put(dis.readString(), dis.readAdvancement());
            }
        }
        listSize = dis.readVarInt();
        identifiers = new String[listSize];
        for (int i = 0; i < listSize; i++) {
            identifiers[i] = dis.readString();
        }
        progressSize = dis.readVarInt();
        progressMapping = new HashMap<>();
        for (int i = 0; i < progressSize; i++) {
            progressMapping.put(dis.readString(), dis.readAdvancementProgress());
        }
    }

}

// Refactored ancestor. Review data strcuture