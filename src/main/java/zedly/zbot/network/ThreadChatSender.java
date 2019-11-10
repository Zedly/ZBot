package zedly.zbot.network;

import zedly.zbot.network.packet.serverbound.Packet03ChatMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import zedly.zbot.StringUtil;

public class ThreadChatSender extends Thread {

    private ThreadUp up;
    final BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in, Charset.forName("ISO-8859-1")));

    public ThreadChatSender(ThreadUp up) {
        this.up = up;
    }

    public void run() {
        while (!isInterrupted()) {
            String s;
            try {
                s = bufferRead.readLine();
                if (s.equals("")) {
                    continue;
                }
                ArrayList<String> chatLines = StringUtil.wrap(s, 250);
                for (String line : chatLines) {
                    sendChat(line);
                    sleep(50);
                }
            } catch (InterruptedException | IOException ex) {
                Logger.getLogger(ThreadChatSender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private synchronized void sendChat(String message) {
        if (up != null) {
            up.sendPacket(new Packet03ChatMessage(message));
        } else {
            System.err.println("No connection! Cannot send chat.");
        }
    }

    public synchronized void changeUpThread(ThreadUp up) {
        this.up = up;
    }

}
