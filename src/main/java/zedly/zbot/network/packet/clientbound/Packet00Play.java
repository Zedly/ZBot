package   zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.Util;
import zedly.zbot.event.SelfKickEvent;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;
import java.util.UUID;


/**
* Sent by the server before it disconnects a client. The client assumes that the server has already closed the connection by the time the packet arrives.
*/


/**
* Sent by the server when a vehicle or other non-living entity is created.
*/

public class Packet00Play implements ClientBoundPacket {
    private int entityID;  // EID of the entity
    private UUID objectUUID;
    private int type;  // The type of entity (same as in <a href="#Spawn_Living_Entity">Spawn Living Entity</a>)
    private double x;
    private double y;
    private double z;
    private int pitch;
    private int yaw;
    private int data;  // Meaning dependent on the value of the Type field, see <a href="/Object_Data" title="Object Data">Object Data</a> for details.
    private int velocityX;  // Same units as <a href="#Entity_Velocity">Entity Velocity</a>.  Always sent, but only used when Data is greater than 0 (except for some entities which always ignore it; see <a href="/Object_Data" title="Object Data">Object Data</a> for details).
    private int velocityY;
    private int velocityZ;


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        entityID = dis.readVarInt();
        objectUUID = dis.readUUID();
        type = dis.readVarInt();
        x = dis.readDouble();
        y = dis.readDouble();
        z = dis.readDouble();
        pitch = dis.readUnsignedByte();
        yaw = dis.readUnsignedByte();
        data = dis.readInt();
        velocityX = dis.readShort();
        velocityY = dis.readShort();
        velocityZ = dis.readShort();
    }

}
