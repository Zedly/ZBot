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
public class Packet13OpenWindow implements ClientBoundPacket {
    int windowID;
    String windowType;
    String windowTitle;
    int numberOfSlots;
    int entityID;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        windowID = dis.readUnsignedByte();
        windowType = dis.readString();
        windowTitle = dis.readString();
        numberOfSlots = dis.readUnsignedByte();
        if (windowType.equals("EntityHorse")) {
            entityID = dis.readInt();
        }
    }
    
    @Override
    public void process(GameContext context) {
        context.getSelf().openWindow(windowType, windowID, numberOfSlots, windowTitle);
        // Emit OpenWindowStartEvent
    }
}