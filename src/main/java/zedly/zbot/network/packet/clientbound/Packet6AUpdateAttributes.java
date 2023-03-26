/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.environment.ModifierData;
import zedly.zbot.environment.PropertyData;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet6AUpdateAttributes implements ClientBoundPacket {
    private int entityID;
    private PropertyData[] properties;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        int count = dis.readInt();
        properties = new PropertyData[count];
        for (int i = 0; i < count; i++) {
            properties[i] = new PropertyData();
            properties[i].key = dis.readString();
            properties[i].value = dis.readDouble();
            int listLength = dis.readVarInt();
            properties[i].modifiers = new ModifierData[listLength];
            for (int i2 = 0; i2 < listLength; i2++) {
                properties[i].modifiers[i2] = new ModifierData();
                properties[i].modifiers[i2].UUID = dis.readUUID();
                properties[i].modifiers[i2].amount = dis.readDouble();
                properties[i].modifiers[i2].operation = dis.readByte();
            }
        }
    }
    
}
//Refactored ancestor. Review data strcutureRefactored ancestor. Review data strcutureRefactored ancestor. Review data strcutureRefactored ancestor. Review data strcuture