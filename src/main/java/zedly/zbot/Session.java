package zedly.zbot;

import com.google.gson.JsonParser;
import java.util.UUID;

public class Session {

    private static final JsonParser parser = new JsonParser();

    private String accessToken;
    private String actualUsername;
    private String dashlessUUID;
    private UUID UUID;
    private boolean onlineMode = true;

    public Session(String actualUsername) {
        this.actualUsername = actualUsername;
    }

    public Session(YamlConfiguration yaml) {        
    }

    public synchronized boolean renew() {
        if (!onlineMode) {
            return true;
        }
        try {
            //System.out.println("Logging in as " + username + "...");
            MicrosoftLoginHelper.MinecraftCredentials mcc = MicrosoftLoginHelper.getSessionToken();

            UUID = mcc.uuid;
            dashlessUUID = mcc.dashlessUUID;
            actualUsername = mcc.username;
            accessToken = mcc.bearerToken;
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
        return dashlessUUID;
    }

    public synchronized boolean isOnlineMode() {
        return onlineMode;
    }
}
