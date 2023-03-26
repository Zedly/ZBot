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
public class Packet55Teams implements ClientBoundPacket {
    private String teamName;
    private byte mode;
    private String teamDisplayName;
    private String teamPrefix;
    private String teamSuffix;
    private boolean friendlyFire;
    private boolean friendlyVisibility;
    private String nameTagVisibility;
    private String collisionRule;
    private int color;
    private String[] players;
    
    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        teamName = dis.readString();
        mode = dis.readByte();
        if (mode == 0 || mode == 2) {
            teamDisplayName = dis.readString();
            byte flags = dis.readByte();
            friendlyFire = (flags & 0x01) != 0;
            friendlyVisibility = (flags & 0x02) != 0;
            nameTagVisibility = dis.readString();
            collisionRule = dis.readString();
            color = dis.readVarInt();
            teamPrefix = dis.readString();
            teamSuffix = dis.readString();
        }
        if (mode == 0 || mode == 3 || mode == 4) {
            int count = dis.readVarInt();
            players = new String[count];
            for (int i = 0; i < count; i++) {
                players[i] = dis.readString();
            }
        }
    }
}
//Refactored ancestor. Review data strcutureRefactored ancestor. Review data strcutureRefactored ancestor. Review data strcutureRefactored ancestor. Review data strcuture