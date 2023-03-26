/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet5BUpdateScore implements ClientBoundPacket {
    private String itemName;
    private byte action;
    private String scoreName;
    private int value;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        itemName = dis.readString();
        action = dis.readByte();
        scoreName = dis.readString();
        if (action != 1) {
            value = dis.readVarInt();
        }
    }   
}
//Refactored ancestor. Review data strcutureRefactored ancestor. Review data strcutureRefactored ancestor. Review data strcutureRefactored ancestor. Review data strcuture