/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.serverbound;

import java.io.IOException;
import zedly.zbot.network.ExtendedDataOutputStream;

/**
 *
 * @author Dennis
 */
public interface ServerBoundPacket {

    int opCode();

    void writePacket(ExtendedDataOutputStream dos) throws IOException;

    default int getSize() {
        return -1;
    }

}
