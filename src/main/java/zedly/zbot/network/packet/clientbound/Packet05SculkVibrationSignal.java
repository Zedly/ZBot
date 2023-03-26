package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.Location;
import zedly.zbot.entity.Entity;
import zedly.zbot.network.ExtendedDataInputStream;

public class Packet05SculkVibrationSignal implements ClientBoundPacket {
    
    private Location source;
    Location destination;
    boolean isDestinationEntity;
    int entityId;
    int timeToArrival;
    
    
    public void readPacket(ExtendedDataInputStream dis) throws IOException {
        Location sourcePosition = dis.readPosition();
        String destinationIdentifier = dis.readString();
        switch(destinationIdentifier) {
            case "block":
                isDestinationEntity = false;
                destination = dis.readPosition();
                break;
            case "entity":
                isDestinationEntity = true;
                int entityId = dis.readVarInt();
                break;
        }
    }
    
    public void process(GameContext context) {
        if(isDestinationEntity) {
            Entity e = context.getSelf().getEnvironment().getEntityById(entityId);
            if(e == null) {
                System.err.println("Sculk Vibration towards non-existing entity");
            }
            destination = e.getLocation();
        }
        // fire event
    }

}