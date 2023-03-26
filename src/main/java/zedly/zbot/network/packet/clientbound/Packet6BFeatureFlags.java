package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

public class Packet6BFeatureFlags implements ClientBoundPacket {
    
    private String[] features;
    
    public void readPacket(ExtendedDataInputStream dis) throws IOException {
        int length = dis.readVarInt();
        features = new String[length];
        for(int i = 0; i < length; i++) {
            features[i] = dis.readString();
        }
    }
}