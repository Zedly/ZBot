package zedly.zbot;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Session {

    private static final JsonParser parser = new JsonParser();

    private String accessToken;
    private String actualUsername;
    private String profileID;
    private final boolean onlineMode;
    private String username;
    private String password;

    public Session(String actualUsername) {
        this.actualUsername = actualUsername;
        onlineMode = false;
    }

    public Session(String username, String password) {
        this.username = username;
        this.password = password;
        onlineMode = true;
    }

    public Session(YamlConfiguration yaml) {
        if (!yaml.getBoolean("onlineMode", false) || yaml.getString("password", "").equals("")) {
            this.username = yaml.getString("user", "ZBot");
            this.actualUsername = username;
            onlineMode = false;
        } else {
            this.username = yaml.getString("user", "ZBot");
            this.password = yaml.getString("password", "");
            onlineMode = true;
        }
    }

    public synchronized boolean renew() {
        if (!onlineMode) {
            return true;
        }
        try {
            //System.out.println("Logging in as " + username + "...");
            HTTP.HTTPResponse http = HTTP.https("https://authserver.mojang.com/authenticate", "{\r\n"
                    + "\"agent\": {\r\n"
                    + "\"name\": \"Minecraft\",\r\n"
                    + "\"version\": 1\r\n\r\n"
                    + "},\r\n"
                    + "\"username\": \"" + username + "\",\r\n"
                    + "\"password\": \"" + password + "\"\r\n"
                    + "}");

            String response = new String(http.getContent());
            JsonElement element = parser.parse(response);
            JsonObject obj = element.getAsJsonObject();
            accessToken = obj.get("accessToken").getAsString();
            JsonObject selectedProfile = obj.get("selectedProfile").getAsJsonObject();
            profileID = selectedProfile.get("id").getAsString();
            actualUsername = selectedProfile.get("name").getAsString();
        } catch (Exception ex) {
            System.out.println("Exception renewing Session:");
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public synchronized String getAccessToken() {
        return accessToken;
    }

    public synchronized String getActualUsername() {
        return actualUsername;
    }

    public synchronized String getProfileID() {
        return profileID;
    }

    public synchronized boolean isOnlineMode() {
        return onlineMode;
    }
}
