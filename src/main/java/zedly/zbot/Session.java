package zedly.zbot;

import java.io.IOException;

public class Session {

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

    public synchronized boolean renew() {
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
            response = response.substring(16);
            int position = response.indexOf("\"");
            accessToken = response.substring(0, position);
            position = response.indexOf("\"id\":");
            response = response.substring(position + 6);
            position = response.indexOf("\"");
            profileID = response.substring(0, position);
            position = response.indexOf("\"name\":");
            response = response.substring(position + 8);
            position = response.indexOf("\"");
            actualUsername = response.substring(0, position);
        } catch (Exception ex) {
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
