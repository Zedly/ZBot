/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.event.SoundEffectEvent;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet46SoundEffect implements ClientBoundPacket {
    private int soundId;
    private int category;
    private int effectPositionX;
    private int effectPositionY;
    private int effectPositionZ;
    private float volume;
    private float pitch;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        soundId = dis.readVarInt();
        category = dis.readVarInt();
        effectPositionX = dis.readInt();
        effectPositionY = dis.readInt();
        effectPositionZ = dis.readInt();
        volume = dis.readFloat();
        pitch = dis.readFloat();
    }
    
    public void process(GameContext context) {
        context.getEventDispatcher().dispatchEvent(new SoundEffectEvent(soundId, category, soundId, soundId, soundId, volume, pitch));
    }
    
}
