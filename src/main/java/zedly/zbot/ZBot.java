package zedly.zbot;

import java.io.File;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class ZBot {

    private static final Yaml YAML = new Yaml();
    private static String username = "ZBot";
    private static String password = "";
    private static short serverPort = 25565;
    private static String serverIp = "127.0.0.1";
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

        //System.out.print("Logging in.. [    ]");

        if (onlineMode) {
            session = new Session(username, password);
            while (!session.renew()) {
                Thread.sleep(60000);
            }
            //System.out.println("logged in as player " + session.getActualUsername());
        } else {
            session = new Session(username);
            //System.out.println("Playing as " + username + " in offline mode");
        }
        ClientSettings clientSettings = new ClientSettings();
        GameContext context = new GameContext(serverIp, serverPort, session, clientSettings);
        context.run();
    }

    private static void loadConfig() throws Exception {
        File file = new File("config.yml");
        if (!file.exists()) {
            writeDefaultConfig();
            return;
        }
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
                throw new Exception("Missing field password in Online Mode!");
            }
        }
        if (map.containsKey("serverIp")) {
            serverIp = (String) map.get("serverIp");
        } else {
            System.out.println("Missing field serverIp. Defaulting to 127.0.0.1");
        }
        if (map.containsKey("serverPort")) {
            serverPort = ((Integer) map.get("serverPort")).shortValue();
        } else {
            System.out.println("Missing field serverPort. Defaulting to 25565");
        }
    }

    private static void writeDefaultConfig() throws IOException {
        System.out.println("Could not find a config.yml!");
        System.out.println("If this is your first start, this is normal.");
        System.out.println("ZBot will now create a config.yml in the working directory.");
        System.out.println("Until you enter your login and server details, the configuration will");
        System.out.println("default to an offline-mode server on localhost.");
        System.out.println("\r\n\r\n\r\n");
        
        String defaultConfig = "user: \"" + username + "\"\n"
                + "password: \"" + password + "\"\n"
                + "serverIp: \"" + serverIp + "\"\n"
                + "serverPort: " + serverPort + "\n"
                + "onlineMode: " + onlineMode + "\n";
        FileOutputStream fos = new FileOutputStream("config.yml");
        fos.write(defaultConfig.getBytes());
        fos.flush();
        fos.close();
    }

}
