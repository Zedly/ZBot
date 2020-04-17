/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot;

import zedly.zbot.self.CraftSelf;
import zedly.zbot.environment.CraftEnvironment;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import zedly.zbot.network.PacketInputStream;
import zedly.zbot.network.PacketOutputStream;
import zedly.zbot.network.ThreadConnectionWatcher;
import zedly.zbot.network.ThreadDown;
import zedly.zbot.network.ThreadLocationUpdater;
import zedly.zbot.network.ThreadUp;
import zedly.zbot.network.ThreadChatSender;
import zedly.zbot.plugin.ZBotPlugin;

/**
 *
 * @author Dennis
 */
public class GameContext {

    private final String serverIp;
    private final int serverPort;
    private final Session session;
    private final YamlConfiguration yaml;
    private final CraftSelf self;
    private final PluginManager pluginManager;

    private ThreadUp threadUp;
    private ThreadDown threadDown;
    private ThreadLocationUpdater locationUpdater;
    private ThreadChatSender chatSender;
    private final ThreadConnectionWatcher connectionWatcher;
    private final ZBotThreadPoolExecutor mainThread;
    private final EventDispatcher eventDispatcher = new EventDispatcher();
    private final HashMap<Integer, Future> taskFutureIds = new HashMap<>();
    private final HashMap<ZBotPlugin, Future> taskFuturePlugins = new HashMap<>();
    private int futureIndex = 0;

    public GameContext(YamlConfiguration yaml, Session session, ClientSettings clientSettings) {
        this.yaml = yaml;
        this.serverIp = yaml.getString("serverIp", "127.0.0.1");
        this.serverPort = yaml.getInt("serverPort", 25565);
        this.session = session;
        connectionWatcher = new ThreadConnectionWatcher(this);
        pluginManager = new PluginManager();
        CraftEnvironment env = new CraftEnvironment();
        self = new CraftSelf(this, env, locationUpdater, clientSettings);
        mainThread = new ZBotThreadPoolExecutor(this, 1);
    }

    public synchronized void openConnection(PacketInputStream is, PacketOutputStream os) {
        threadUp = new ThreadUp(os);
        threadDown = new ThreadDown(is, this);
        locationUpdater = new ThreadLocationUpdater(this);
        self.setClientSettings(self.getClientSettings()); // Force re-send if online mode
        threadUp.start();
        threadDown.start();
        if (chatSender != null) {
            chatSender.changeUpThread(threadUp);
        } else {
            chatSender = new ThreadChatSender(threadUp);
            chatSender.start();
        }
    }

    public synchronized void launch() {
        pluginManager.loadPlugins();
        pluginManager.enablePlugins(self);
        connectionWatcher.start();
    }

    public synchronized void finish() {
        mainThread.shutdownNow();
        connectionWatcher.interrupt();
        pluginManager.disablePlugins();
    }

    public synchronized void closeConnection() {
        pluginManager.onQuit();
        for (int i : taskFutureIds.keySet()) {
            taskFutureIds.get(i).cancel(false);
        }
        if (threadUp != null) {
            threadUp.interrupt();
        }
        if (threadDown != null) {
            threadDown.interrupt();
        }
        if (locationUpdater != null) {
            locationUpdater.interrupt();
        }
        if (chatSender != null) {
            chatSender.changeUpThread(null);
        }
    }

    public synchronized void cancelTask(int i) {
        taskFutureIds.get(i).cancel(false);
        mainThread.purge();
    }

    public synchronized ThreadDown getDownThread() {
        return threadDown;
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

    public synchronized String getServerIp() {
        return serverIp;
    }

    public synchronized int getServerPort() {
        return serverPort;
    }

    public synchronized Session getSession() {
        return session;
    }
    
    public synchronized YamlConfiguration getClientConfig() {
        return yaml;
    }

    public synchronized ThreadUp getUpThread() {
        return threadUp;
    }

    public void joinThreads() throws InterruptedException {
        threadDown.join();
        //threadUp.join();
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

}
