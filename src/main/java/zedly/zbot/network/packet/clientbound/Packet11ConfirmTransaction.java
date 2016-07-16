/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet11ConfirmTransaction implements ClientBoundPacket {
    private int windowID;
    private short actionNumber;
    private boolean accepted;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        windowID = dis.readByte();
        actionNumber = dis.readShort();
        accepted = dis.readBoolean();
    }
    
    public void process(GameContext context) {
        System.out.println("Transaction ID " + actionNumber + ": " + accepted);
    }

    public int getWindowID() {
        return windowID;
    }

    public short getActionNumber() {
        return actionNumber;
    }

    public boolean isAccepted() {
        return accepted;
    }
    
}
