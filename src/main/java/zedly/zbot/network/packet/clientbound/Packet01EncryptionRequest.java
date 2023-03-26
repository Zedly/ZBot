/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.packet.clientbound.ClientBoundPacket;

/**
 *
 * @author Dennis
 */
public class Packet01EncryptionRequest implements ClientBoundPacket {
    
    private String serverID;
    private byte[] publicKey;
    private byte[] verifyToken;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        serverID = dis.readString();
        int len = dis.readVarInt();
        publicKey = new byte[len];
        dis.readFully(publicKey);
        len = dis.readVarInt();
        verifyToken = new byte[len];
        dis.readFully(verifyToken);
    }

    public String getServerID() {
        return serverID;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public byte[] getVerifyToken() {
        return verifyToken;
    }
    
}
// Refactored ancestor. Review data strcutureRefactored ancestor. Review data strcuture