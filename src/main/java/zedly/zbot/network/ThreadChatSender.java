package zedly.zbot.network;

import zedly.zbot.network.packet.serverbound.Packet02ChatMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import zedly.zbot.GameContext;
import zedly.zbot.StringUtil;

public class ThreadChatSender extends Thread {

    private final int maxChatLineLength = 100;
    private final GameContext context;
    final BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in, Charset.forName("ISO-8859-1")));

    public ThreadChatSender(GameContext context) {
        this.context = context;
    }

    public void run() {
        while (!isInterrupted()) {
            String s;
            try {
                s = bufferRead.readLine();
                ArrayList<String> chatLines = StringUtil.wrap(s, maxChatLineLength);
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
        context.getUpThread().sendPacket(new Packet02ChatMessage(message));
    }
}
