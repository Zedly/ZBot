package  zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.event.SoundEffectEvent;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */

/**
* This packet is used to play a number of hardcoded sound events. For custom sounds, use <a href="#Named_Sound_Effect">Named Sound Effect</a>.
*/

public class Packet49SoundEffect implements ClientBoundPacket {
    private int soundID;  // ID of hardcoded sound event (<a rel="nofollow" class="external text" href="http://pokechu22.github.io/Burger/1.12.2.html#sounds">events</a> as of 1.12.2)
    private int soundCategory;  // The category that this sound will be played from (<a rel="nofollow" class="external text" href="https://gist.github.com/konwboj/7c0c380d3923443e9d55">current categories</a>)
    private int effectPositionX;  // Effect X multiplied by 8 (<a href="/Data_types#Fixed-point_numbers" title="Data types">fixed-point number</a> with only 3 bits dedicated to the fractional part)
    private int effectPositionY;  // Effect Y multiplied by 8 (<a href="/Data_types#Fixed-point_numbers" title="Data types">fixed-point number</a> with only 3 bits dedicated to the fractional part)
    private int effectPositionZ;  // Effect Z multiplied by 8 (<a href="/Data_types#Fixed-point_numbers" title="Data types">fixed-point number</a> with only 3 bits dedicated to the fractional part)
    private double volume;  // 1.0 is 100%, capped between 0.0 and 1.0 by Notchian clients
    private double pitch;  // Float between 0.5 and 2.0 by Notchian clients


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        soundID = dis.readVarInt();
        soundCategory = dis.readVarInt();
        effectPositionX = dis.readInt();
        effectPositionY = dis.readInt();
        effectPositionZ = dis.readInt();
        volume = dis.readFloat();
        pitch = dis.readFloat();
    }

    @Override
    public void process(GameContext context) {
        context.getEventDispatcher().dispatchEvent(new SoundEffectEvent(soundID, soundCategory, effectPositionX, effectPositionY, effectPositionZ, volume, pitch));
    }

}
