package zedly.zbot.network;

import zedly.zbot.ConcurrentLinkedQueue;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadUp extends Thread {

    private final ConcurrentLinkedQueue<ServerBoundPacket> outgoingBuffer = new ConcurrentLinkedQueue<>();
    private final ExtendedDataOutputStream dos;
    private final ByteArrayOutputStream byteBuffer;
    private final ExtendedDataOutputStream dataBuffer;
    private final int[] lastOps = new int[16];
    private int opPtr = 0;

    public ThreadUp(OutputStream os) {
        dos = new ExtendedDataOutputStream(os);
        byteBuffer = new ByteArrayOutputStream();
        dataBuffer = new ExtendedDataOutputStream(byteBuffer);
    }

    @Override
    public void run() {
        try {
            while (true) {
                ServerBoundPacket p = outgoingBuffer.deq();
                lastOps[(opPtr++) % 16] = p.opCode();
                //System.out.println("ThreadUp Debug: Op " + p.opCode());
                byteBuffer.reset();
                dataBuffer.writeVarInt(p.opCode());
                p.writePacket(dataBuffer);
                dataBuffer.flush();
                dos.writeVarInt(byteBuffer.size() + 1);
                dos.writeVarInt(0);
                dos.write(byteBuffer.toByteArray());
            }
        } catch (InterruptedException ex) {
            System.out.println("ThreadUp interrupted");
            System.out.print("Last sent packets: ");
            for(int i = opPtr; i <opPtr + 16; i++) {
                System.out.print(Integer.toHexString(lastOps[(opPtr + i) % 16]) + " ");
            }
            System.out.print("\n");
            //ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(ThreadUp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendPacket(ServerBoundPacket p) {
        outgoingBuffer.enq(p);
    }
}
