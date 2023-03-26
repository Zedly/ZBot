package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import java.util.UUID;
import zedly.zbot.network.ExtendedDataOutputStream;

public class Packet06PlayerSession implements ServerBoundPacket {
    
    private final UUID sessionID;
    private final long expiresAt;
    private byte[] publicKey;
    private byte[] keySignature;
    
    public Packet06PlayerSession(UUID sessionID, long expiresAt, byte[] publicKey, byte[] keySignature) {
        this.sessionID = sessionID;
        this.expiresAt = expiresAt;
        this.publicKey = publicKey;
        this.keySignature = keySignature;
    }
    
    @Override
    public void writePacket(ExtendedDataOutputStream dos) throws IOException {
        dos.writeUUID(sessionID);
        dos.writeLong(expiresAt);
        dos.writeVarInt(publicKey.length);
        dos.write(publicKey);
        dos.writeVarInt(keySignature.length);
        dos.write(keySignature);
   
    }

    @Override
    public int opCode() { return 0x06; }

}