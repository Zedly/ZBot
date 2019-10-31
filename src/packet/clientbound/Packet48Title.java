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
public class Packet48Title implements ClientBoundPacket {
    private int action;
    private String titleText;
    private String actionBarText;
    private String subtitleText;
    private int fadeInTime;
    private int stayTime;
    private int fadeOutTime;
    
    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        action = dis.readVarInt();
        switch (action) {
            case 0:
                titleText = dis.readString();
                break;
            case 1:
                subtitleText = dis.readString();
                break;
            case 2:
                actionBarText = dis.readString();
            case 3:
                fadeInTime = dis.readInt();
                stayTime = dis.readInt();
                fadeOutTime = dis.readInt();
        }
    }
    
}
//Refactored ancestor. Review data strcuture