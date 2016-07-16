/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.api.event.HealthChangeEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

/**
 * @author Dennis
 */
public class Packet3EUpdateHealth implements ClientBoundPacket {

    private float health;
    private int food;
    private float foodSaturation;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        health = dis.readFloat();
        food = dis.readVarInt();
        foodSaturation = dis.readFloat();
    }

    @Override
    public void process(GameContext context) {
        context.getMainThread().fireEvent(new HealthChangeEvent(health, food, foodSaturation));
    }
}
