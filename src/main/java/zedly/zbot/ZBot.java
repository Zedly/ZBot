package zedly.zbot;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.util.Map;

public class ZBot {

    private static final Yaml YAML = new Yaml();
    private static String username = "ZBot";
    private static String password = "";
    private static int serverPort = 25565;
    private static String serverIP = "127.0.0.1";
    private static boolean onlineMode = false;

    public static void main(String[] args) throws Exception {        
        Session session;

        try {
            loadConfig();
        } catch (Exception ex) {
            System.err.println("Unable to load configuration. Please fix the yml");
            ex.printStackTrace();
            return;
        }

        System.out.print("Logging in.. [    ]");

        if (onlineMode) {
            session = new Session(username, password);
            while (!session.renew()) {
                System.out.println("\rLogging in.. [FAIL]");
                System.out.println("Login to Mojang failed. Waiting to retry...");
                Thread.sleep(60000);
                System.out.print("Logging in.. [    ]");
            }
            //System.out.println("logged in as player " + session.getActualUsername());
        } else {
            session = new Session(username); 
            //System.out.println("Playing as " + username + " in offline mode");
        }
        ClientSettings clientSettings = new ClientSettings();
        GameContext context = new GameContext(serverIP, serverPort, session, clientSettings);
        context.launch();
    }

    private static void loadConfig() throws Exception {
        FileInputStream fis = new FileInputStream("config.yml");
        Map map = (Map) YAML.load(fis);
        if (map.containsKey("user")) {
            username = (String) map.get("user");
        } else {
            System.out.println("Missing field user. Defaulting to ZBot");
        }
        if (map.containsKey("onlineMode")) {
            onlineMode = (boolean) map.get("onlineMode");
        } else {
            System.out.println("Missing field onlineMode. Defaulting to false");
        }
        if (onlineMode) {
            if (map.containsKey("password")) {
                password = (String) map.get("password");
            } else {
                System.out.println("Missing field password in Online Mode! Falling back to Offline Mode.");
                onlineMode = false;
            }
        }
        if (map.containsKey("serverIp")) {
            serverIP = (String) map.get("serverIp");
        } else {
            System.out.println("Missing field serverIp. Defaulting to 127.0.0.1");
        }
        if (map.containsKey("serverPort")) {
            serverPort = (Integer) map.get("serverPort");
        } else {
            System.out.println("Missing field serverPort. Defaulting to 25565");
        }
    }
}
