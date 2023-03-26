package  zedly.zbot.network.packet.clientbound;

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
* Used to play a sound effect on the client. Custom sounds may be added by resource packs.
*/

public class Packet19NamedSoundEffect implements ClientBoundPacket {
    private String soundName;  // All sound effect names as of 1.16.5 can be seen <a rel="nofollow" class="external text" href="https://pokechu22.github.io/Burger/1.16.5.html#sounds">here</a>.
    private int soundCategory;  // The category that this sound will be played from (<a rel="nofollow" class="external text" href="https://gist.github.com/konwboj/7c0c380d3923443e9d55">current categories</a>).
    private int effectPositionX;  // Effect X multiplied by 8 (<a href="/Data_types#Fixed-point_numbers" title="Data types">fixed-point number</a> with only 3 bits dedicated to the fractional part).
    private int effectPositionY;  // Effect Y multiplied by 8 (<a href="/Data_types#Fixed-point_numbers" title="Data types">fixed-point number</a> with only 3 bits dedicated to the fractional part).
    private int effectPositionZ;  // Effect Z multiplied by 8 (<a href="/Data_types#Fixed-point_numbers" title="Data types">fixed-point number</a> with only 3 bits dedicated to the fractional part).
    private double volume;  // 1 is 100%, can be more.
    private double pitch;  // Float between 0.5 and 2.0 by Notchian clients.


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        soundName = dis.readString();
        soundCategory = dis.readVarInt();
        effectPositionX = dis.readInt();
        effectPositionY = dis.readInt();
        effectPositionZ = dis.readInt();
        volume = dis.readFloat();
        pitch = dis.readFloat();
    }

    @Override
    public void process(GameContext context) {
//        System.out.println("Debug: Sound effect " + soundName);
        context.getEventDispatcher().dispatchEvent(new NamedSoundEffectEvent(soundName, soundCategory, effectPositionX, effectPositionY, effectPositionZ, volume, pitch));    }

}
