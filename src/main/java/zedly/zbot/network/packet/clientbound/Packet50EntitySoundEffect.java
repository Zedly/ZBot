package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.event.NamedSoundEffectEvent;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
/**
 * Used to play a sound effect on the client. Custom sounds may be added by
 * resource packs.
 */
/**
 * Plays a sound effect from an entity.
 */
public class Packet50EntitySoundEffect implements ClientBoundPacket {

    private int soundID;  // ID of hardcoded sound event (<a rel="nofollow" class="external text" href="https://pokechu22.github.io/Burger/1.14.4.html#sounds">events</a> as of 1.14.4)
    private int soundCategory;  // The category that this sound will be played from (<a rel="nofollow" class="external text" href="https://gist.github.com/konwboj/7c0c380d3923443e9d55">current categories</a>)
    private int entityID;
    private double volume;  // 1.0 is 100%, capped between 0.0 and 1.0 by Notchian clients
    private double pitch;  // Float between 0.5 and 2.0 by Notchian clients

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        soundID = dis.readVarInt();
        soundCategory = dis.readVarInt();
        entityID = dis.readVarInt();
        volume = dis.readFloat();
        pitch = dis.readFloat();
    }

    @Override
    public void process(GameContext context) {
        System.out.println("Debug: Entity sound effect " + soundID);
        //context.getEventDispatcher().dispatchEvent(new NamedSoundEffectEvent(soundName, soundCategory, effectPositionX, effectPositionY, effectPositionZ, volume, pitch));
    }

}
