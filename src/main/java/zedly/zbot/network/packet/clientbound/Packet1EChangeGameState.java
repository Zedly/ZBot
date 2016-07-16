/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.api.event.*;
import zedly.zbot.api.event.entity.EntityArrowHitEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

/**
 * @author Dennis
 */
public class Packet1EChangeGameState implements ClientBoundPacket {

    private int reason;
    private float value;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        reason = dis.readUnsignedByte();
        value = dis.readFloat();
    }

    @Override
    public void process(GameContext context) {
        Event fire = null;
        switch (reason) {
            case 0:
                fire = new InvalidBedEvent(value);
                break;
            case 1:
                fire = new RainChangeEvent(true, value);
                break;
            case 2:
                fire = new RainChangeEvent(false, value);
                break;
            case 3:
                fire = new ChangeGamemodeEvent(value);
                break;
            case 4:
                fire = new EnterCreditsEvent(value);
                break;
            case 5:
                fire = new DemoMessageEvent(value);
                break;
            case 6:
                fire = new EntityArrowHitEvent(value);
                break;
            case 7:
            case 8:
                fire = new SkyFadeEvent(value);
                break;
            case 10:
                break;
        }
        if (fire != null) {
            context.getMainThread().fireEvent(fire);
        }
    }
}
