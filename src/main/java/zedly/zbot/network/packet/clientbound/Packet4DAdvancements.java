package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import java.util.HashMap;
import zedly.zbot.Advancement;
import zedly.zbot.AdvancementProgress;
import zedly.zbot.network.ExtendedDataInputStream;

public class Packet4DAdvancements implements ClientBoundPacket {

    private boolean resetClear;
    private int mappingSize;
    private HashMap<String, Advancement> advancementMapping;
    private int listSize;
    private String[] identifiers;
    private int progressSize;
    private HashMap<String, AdvancementProgress> progressMapping;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        resetClear = dis.readBoolean();
        mappingSize = dis.readVarInt();
        advancementMapping = new HashMap<>();
        for(int i = 0; i < mappingSize; i++) {
            advancementMapping.put(dis.readString(), dis.readAdvancement());
        }
        listSize = dis.readVarInt();
        identifiers = new String[listSize];
        for(int i = 0; i < listSize; i++) {
            identifiers[i] = dis.readString();
        }
        progressSize = dis.readVarInt();
        progressMapping = new HashMap<>();
        for(int i = 0; i < progressSize; i++) {
            progressMapping.put(dis.readString(), dis.readAdvancementProgress());
        }
    }

}
//Skeleton with unreadable structure and no ancestor. Implement manually
