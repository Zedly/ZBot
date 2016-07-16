/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

/**
 * @author Dennis
 */
public class Packet23JoinGame implements ClientBoundPacket {

    private int entityID;
    private int gamemode;
    private int dimension;
    private int difficulty;
    private int maxPlayers;
    private String levelType;
    private boolean debugInfo;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readInt();
        gamemode = dis.readUnsignedByte();
        dimension = dis.readInt();
        difficulty = dis.readUnsignedByte();
        maxPlayers = dis.readUnsignedByte();
        levelType = dis.readString();
        debugInfo = dis.readBoolean();
    }

    @Override
    public void process(GameContext context) {
        context.getSelf().setEntityId(entityID);
        context.getPluginManager().onJoin();
        //context.getMainThread().fireEvent(new PlayerJoinEvent(entityID, gamemode));
    }
}
