/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import org.yaml.snakeyaml.Yaml;
import zedly.zbot.plugin.ZBotPlugin;
import zedly.zbot.self.Self;

/**
 *
 * @author Dennis
 */
public class PluginManager {

    private final HashMap<String, ZBotPlugin> plugins = new HashMap<>();
    private final Yaml yaml = new Yaml();

    public synchronized void loadPlugins() {
        try {
            File pluginDir = new File("plugins");
            if (!pluginDir.exists()) {
                pluginDir.mkdir();
            }

            // List Plugin files
            File[] pluginFiles = pluginDir.listFiles((File dir, String name) -> name.matches("(.+)\\.jar"));

            // Convert them to URLs
            URL[] pluginUrls = new URL[pluginFiles.length];
            for (int i = 0; i < pluginFiles.length; i++) {
                pluginUrls[i] = pluginFiles[i].toURI().toURL();
            }

            // Create ClassLoader
            // Find Main classes in plugin ymls
            for (URL pluginUrl : pluginUrls) {
                URLClassLoader loader = URLClassLoader.newInstance(new URL[]{pluginUrl}, ZBot.class.getClassLoader());
                YamlConfiguration pluginYml = YamlConfiguration.read(loader.getResourceAsStream("plugin.yml"));
                String mainClass = pluginYml.getString("main", null);
                String pluginName = pluginYml.getString("name", null);
                if (mainClass == null || pluginName == null) {
                    System.err.println("Could not load " + pluginUrl.getPath() + ": Malformed plugin.yml");
                    continue;
                }
                if (plugins.containsKey(pluginName)) {
                    System.err.println("Plugin " + pluginName + " is already registered!");
                    continue;
                }
                System.out.println("Loading Plugin: " + pluginName);
                try {
                    // Instantiate Main classes!
                    Class clazz = Class.forName(mainClass, true, loader);
                    Class<? extends ZBotPlugin> pluginClass = clazz.asSubclass(ZBotPlugin.class);
                    Constructor<? extends ZBotPlugin> ctor = pluginClass.getConstructor();
                    ZBotPlugin plugin = ctor.newInstance();
                    // Awwwww
                    try {
                        plugin.reloadConfig();
                        plugin.onLoad();
                        plugins.put(pluginName, plugin);
                    } catch (Exception ex) {
                        System.err.println("Error loading plugin " + pluginName + "!");
                        ex.printStackTrace();
                    }
                    // Yissss
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }

    public synchronized void enablePlugins(Self self) {
        for (String pluginName : plugins.keySet()) {
            ZBotPlugin plugin = plugins.get(pluginName);
            try {
                plugin.onEnable(self);
            } catch (Exception ex) {
                System.err.println("Error enabling plugin " + pluginName + "!");
                ex.printStackTrace();
            }
        }
    }

    public synchronized void disablePlugins() {
        for (String pluginName : plugins.keySet()) {
            ZBotPlugin plugin = plugins.get(pluginName);
            try {
                plugin.onDisable();
            } catch (Exception ex) {
                System.err.println("Error disabling plugin " + pluginName + "!");
                ex.printStackTrace();
            }
        }
    }

    public synchronized void onJoin() {
        for (String pluginName : plugins.keySet()) {
            ZBotPlugin plugin = plugins.get(pluginName);
            try {
                plugin.onJoin();
            } catch (Exception ex) {
                System.err.println("Error notifying plugin " + pluginName + " of Join!");
                ex.printStackTrace();
            }
        }
    }

    public synchronized void onQuit() {
        for (String pluginName : plugins.keySet()) {
            ZBotPlugin plugin = plugins.get(pluginName);
            try {
                plugin.onQuit();
            } catch (Exception ex) {
                System.err.println("Error notifying plugin " + pluginName + " of Quit!");
                ex.printStackTrace();
            }
        }
    }
}
