/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

/**
 *
 * @author Dennis
 */
public class Packet01EncryptionResponse implements ServerBoundPacket {
    
    byte[] sharedSecret;
    byte[] verifyToken;

    public Packet01EncryptionResponse() {
    }

    public Packet01EncryptionResponse(byte[] sharedSecret, byte[] verifyToken) {
        this.sharedSecret = sharedSecret;
        this.verifyToken = verifyToken;
    }

    @Override
    public int opCode() {
        return 0x01;
    }

    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeVarInt(sharedSecret.length);
        for (int x = 0; x < sharedSecret.length; x++) {
            dos.write(sharedSecret[x]);
        }
        dos.writeVarInt(verifyToken.length);
        for (int x = 0; x < verifyToken.length; x++) {
            dos.write(verifyToken[x]);
        }
    }
    
}
// Refactored ancestor. Review data strcutureRefactored ancestor. Review data strcutureRefactored ancestor. Review data strcuture