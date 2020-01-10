package   zedly.zbot.network.packet.clientbound;

import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;


/**
* */


/**
* */

public class Packet39ResourcePackSend implements ClientBoundPacket {
    private String uRL;  // The URL to the resource pack.
    private String hash;  // A 40 character hexadecimal and lowercase <a href="http://en.wikipedia.org/wiki/SHA-1" class="extiw" title="wikipedia:SHA-1">SHA-1</a> hash of the resource pack file. (must be lower case in order to work)<br />If it's not a 40 character hexadecimal string, the client will not use it for hash verification and likely waste bandwidth — but it will still treat it as a unique id


    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        uRL = dis.readString();
        hash = dis.readString();
    }

}
