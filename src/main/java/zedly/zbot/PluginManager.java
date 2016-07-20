/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
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

    public void loadPlugins() {
        try {
            File pluginDir = new File("plugins");
            if (!pluginDir.exists()) {
                pluginDir.mkdir();
            }

            // List Plugin files
            File[] pluginFiles = pluginDir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.matches("(.+)\\.jar");
                }
            });

            // Convert them to URLs
            URL[] pluginUrls = new URL[pluginFiles.length];
            for (int i = 0; i < pluginFiles.length; i++) {
                pluginUrls[i] = pluginFiles[i].toURI().toURL();
            }

            // Create ClassLoader
            ClassLoader loader = URLClassLoader.newInstance(pluginUrls, ZBot.class.getClassLoader());
            Enumeration<URL> pluginYmls = loader.getResources("plugin.yml");

            // Find Main classes in plugin ymls
            while (pluginYmls.hasMoreElements()) {
                try {
                    URL url = pluginYmls.nextElement();
                    Map map = (Map) yaml.load(url.openStream());

                    if (!map.containsKey("main") || !map.containsKey("name")) {
                        System.err.println("Could not load " + url.getPath() + ": Malformed plugin.yml");
                        continue;
                    }

                    String mainClass = (String) map.get("main");
                    String pluginName = (String) map.get("name");
                    System.out.println("Loading Plugin: " + pluginName);

                    if(plugins.containsKey(pluginName)) {
                        System.err.println("Plugin " + pluginName + " is already registered!");
                        continue;
                    }
                    
                    // Instantiate Main classes!
                    Class clazz = Class.forName(mainClass, true, loader);
                    Class<? extends ZBotPlugin> pluginClass = clazz.asSubclass(ZBotPlugin.class);
                    Constructor<? extends ZBotPlugin> ctor = pluginClass.getConstructor();
                    ZBotPlugin plugin = ctor.newInstance();

                    // Awwwww
                    try {
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
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void enablePlugins(Self self) {
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

    public void disablePlugins() {
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
    
    public void onJoin() {
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
    
    public void onQuit() {
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
