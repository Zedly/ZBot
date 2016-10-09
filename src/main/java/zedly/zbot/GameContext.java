/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot;

import java.io.IOException;
import zedly.zbot.self.CraftSelf;
import zedly.zbot.environment.CraftEnvironment;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import zedly.zbot.network.ThreadDown;
import zedly.zbot.network.ThreadLocationUpdater;
import zedly.zbot.network.ThreadUp;
import zedly.zbot.network.ThreadChatSender;
import zedly.zbot.plugin.ZBotPlugin;
import static java.lang.Thread.sleep;
import java.net.InetSocketAddress;
import zedly.zbot.network.Packet.Packet00Disconnect;

/**
 *
 * @author Dennis
 */
public class GameContext {

    private final InetSocketAddress serverAddress;
    private final Session session;
    private final CraftSelf self;
    private final PluginManager pluginManager;

    private final ZBotThreadPoolExecutor mainThread;
    private final EventDispatcher eventDispatcher = new EventDispatcher();
    private final HashMap<Integer, Future> taskFutureIds = new HashMap<>();

    private ThreadUp threadUp;
    private ThreadDown threadDown;
    private ThreadLocationUpdater locationUpdater;
    private int futureIndex = 0;
    private boolean reconnect = true;

    public GameContext(String serverIp, int serverPort, Session session, ClientSettings clientSettings) {
        this.serverAddress = new InetSocketAddress(serverIp, serverPort);
        this.session = session;
        pluginManager = new PluginManager();
        CraftEnvironment env = new CraftEnvironment();
        self = new CraftSelf(this, env, locationUpdater, clientSettings);
        mainThread = new ZBotThreadPoolExecutor(this, 1);
    }

    public void run() {
        ThreadChatSender chatSender = new ThreadChatSender(this);
        try {
            renewSession();
            pluginManager.loadPlugins();
            pluginManager.enablePlugins(self);
            chatSender.start();
            while (reconnect) {
                GameSocket gameSocket = new GameSocket(getServerAddress(), session);
                System.out.print("Connecting to server..");
                if (!gameSocket.connect()) {
                    System.err.println("Could not connect to server. Retrying in 30 seconds");
                    sleep(30000);
                    continue;
                }
                System.out.print("Logging in..");
                if (!gameSocket.handshake()) {
                    System.err.println("Unable to log in!");
                    if (gameSocket.getDisconnectPacket() != null) {
                        handleLoginRejection(gameSocket.getDisconnectPacket());
                    }
                    sleep(30000);
                    continue;
                }
                threadUp = new ThreadUp(gameSocket);
                threadDown = new ThreadDown(gameSocket);
                locationUpdater = new ThreadLocationUpdater(this);
                self.setClientSettings(self.getClientSettings()); // Force re-send if online mode
                System.out.println("Loading terrain..");
                threadUp.start();
                threadDown.start();
                
                
                
                // Main game logic here
                
                
                
            }
            mainThread.shutdownNow();
            pluginManager.disablePlugins();
        } catch (InterruptedException ex) {
        }
    }

    private void handleLoginRejection(Packet00Disconnect disconnectPacket) {
        String kickReason = disconnectPacket.getJSONData();
        System.out.println("Kicked: " + kickReason);
        if (kickReason.contains("40")) {
            renewSession();
        }
    }

    private void renewSession() {
        try {
            while (true) {
                System.out.println("Getting session..");
                if (session.renew()) {
                    break;
                }
                System.err.println("Unable to get session. Retrying in 30 seconds");
                sleep(30000);
            }
        } catch (InterruptedException ex) {
        }
    }

    public synchronized void disconnect() {
    }

    public synchronized void cleanUpConnection() {
        pluginManager.onQuit();
        for (int i : taskFutureIds.keySet()) {
            taskFutureIds.get(i).cancel(false);
        }
        locationUpdater.exit();
    }

    public synchronized void cancelTask(int i) {
        taskFutureIds.get(i).cancel(false);
        mainThread.purge();
    }

    public synchronized EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    public synchronized ThreadLocationUpdater getLocationUpdater() {
        return locationUpdater;
    }

    public synchronized ZBotThreadPoolExecutor getMainThread() {
        return mainThread;
    }

    public synchronized CraftSelf getSelf() {
        return self;
    }

    public synchronized Session getSession() {
        return session;
    }

    public synchronized ThreadUp getUpThread() {
        return threadUp;
    }

    public synchronized int scheduleSyncDelayedTask(ZBotPlugin plugin, Runnable r, long delay) {
        Future f = mainThread.schedule(() -> {
            try {
                r.run();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, delay, TimeUnit.MILLISECONDS);
        taskFutureIds.put(futureIndex, f);
        return futureIndex++;
    }

    public synchronized int scheduleSyncRepeatingTask(ZBotPlugin plugin, Runnable r, long delay, long interval) {
        Future f = mainThread.scheduleAtFixedRate(() -> {
            try {
                r.run();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, delay, interval, TimeUnit.MILLISECONDS);
        taskFutureIds.put(futureIndex, f);
        return futureIndex++;
    }

    public synchronized PluginManager getPluginManager() {
        return pluginManager;
    }

    /**
     * @return the serverAddress
     */
    public InetSocketAddress getServerAddress() {
        return serverAddress;
    }
}
