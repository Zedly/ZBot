package zedly.zbot;

import java.io.File;

public class ZBot {

    public static void main(String[] args) throws Exception {
        YamlConfiguration yaml = YamlConfiguration.read(new File("config.yml"));

        try {
            Session session = new Session(yaml);
            ClientSettings clientSettings = new ClientSettings();

            System.out.print("Logging in.. [    ]");

            while (!session.renew()) {
                System.out.println("\rLogging in.. [FAIL]");
                System.out.println("Login to Mojang failed. Waiting to retry...");
                Thread.sleep(60000);
                System.out.print("Logging in.. [    ]");
            }

            System.out.println("Connecting as player " + session.getActualUsername());

            GameContext context = new GameContext(yaml, session, clientSettings);
            context.launch();
        } catch (Exception ex) {
            System.err.println("Unable to load configuration. Please fix the yml");
            ex.printStackTrace();
            return;
        }
    }
}
