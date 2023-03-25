/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zedly.zbot;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

/**
 *
 * @author Dennis
 */
public class MicrosoftLoginHelper {

    private static final File SESSION_FILE = new File("session.yml");

    public static String getSessionToken() throws IOException {

        YamlConfiguration sessionConf = YamlConfiguration.read(SESSION_FILE);
        AccessToken at = new AccessToken(sessionConf);

        do {
            boolean needNewMicrosoftToken = true;

            if (at.refreshToken != null) {
                at = refreshPasswordlessAccessCredentials(at);
                needNewMicrosoftToken = at.accessToken == null;
            }

            if (needNewMicrosoftToken) {
                String microsoftToken = getMicrosoftToken();
                at = getPasswordlessAccessCredentials(microsoftToken);
                continue;
            }

            at.writeToFile(sessionConf);
            sessionConf.save(SESSION_FILE);

            XBLCredentials xblc = getXBLSessionData(at.accessToken);
            
            String xstsToken = getXSTSToken(xblc);
            
            String minecraftBearerToken = getMinecraftBearerToken(xblc, xstsToken);
            return minecraftBearerToken;
            // get XSTS Token
            // Get MC token
            // return MC token
        } while (true);
    }

    private static final byte[] AUTH_RESPONSE_PAGE_BYTES = ("HTTP 200 OK\r\n"
            + "Content-Length: 177\r\n"
            + "\r\n"
            + "<html><head></head><body><script type=\"text/javascript\">window.close();</script><h1>ZBot</h1>The response from Microsoft has been received. You can close this tab!</body></html>")
            .getBytes();

    public static String getMicrosoftToken() throws IOException {
        System.out.println("");
        System.out.println("    ****    Authorize ZBot to log in to Microsoft with your account   ***    ");
        System.out.println("");
        System.out.println(" Please copy and open the following URL in your browser. It will give ZBot the ability to log in to Xbox Live using your username and password.");
        System.out.println("");
        System.out.println("https://login.microsoftonline.com/consumers/oauth2/v2.0/authorize?client_id=abbb8974-83d9-4162-93ba-bf9137790961&response_type=code&redirect_uri=http%3A%2F%2Flocalhost:2380%2Fauth-response&response_mode=query&scope=XboxLive.signin%20offline_access&state=12345&code_challenge=YTFjNjI1OWYzMzA3MTI4ZDY2Njg5M2RkNmVjNDE5YmEy&code_challenge_method=plain");
        System.out.println("");
        System.out.println("");

        String token = "";
        ServerSocket ss = new ServerSocket(2380);
        while (true) {
            Socket cs = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            String greeting = in.readLine();
            cs.getOutputStream().write(AUTH_RESPONSE_PAGE_BYTES, 0, AUTH_RESPONSE_PAGE_BYTES.length);
            cs.getOutputStream().flush();
            cs.close();
            if (greeting != null && greeting.contains("GET /auth-response?code=")) {
                token = StringUtil.extract(greeting, "?code=", "&state");
                break;
            }
        }
        ss.close();

        return token;
    }

    public static AccessToken getPasswordlessAccessCredentials(String msaToken) throws IOException {
        HttpResponse<JsonNode> response = Unirest.post("https://login.microsoftonline.com/consumers/oauth2/v2.0/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body("client_id=abbb8974-83d9-4162-93ba-bf9137790961"
                        + "&scope=XboxLive.signin"
                        + "&code=" + msaToken
                        + "&redirect_uri=http%3A%2F%2Flocalhost:2380%2Fauth-response"
                        + "&grant_type=authorization_code"
                        + "&code_verifier=YTFjNjI1OWYzMzA3MTI4ZDY2Njg5M2RkNmVjNDE5YmEy").asJson();
        System.out.println(response.getBody());
        AccessToken at = new AccessToken(response.getBody().getObject());
        return at;
    }

    private static AccessToken refreshPasswordlessAccessCredentials(AccessToken at) throws IOException {
        HttpResponse<JsonNode> response = Unirest.post("https://login.microsoftonline.com/consumers/oauth2/v2.0/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body("client_id=abbb8974-83d9-4162-93ba-bf9137790961"
                        + "&scope=XboxLive.signin%20offline_access"
                        + "&refresh_token=" + at.refreshToken
                        + "&grant_type=refresh_token").asJson();
        System.out.println(response.getBody());
        at = new AccessToken(response.getBody().getObject());
        return at;
    }

    public static XBLCredentials getXBLSessionData(String accessToken) throws IOException {
        HttpResponse<JsonNode> xbox_response = Unirest.post("https://user.auth.xboxlive.com/user/authenticate")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body("{"
                        + "    \"Properties\": {"
                        + "        \"AuthMethod\": \"RPS\","
                        + "        \"SiteName\": \"user.auth.xboxlive.com\","
                        + "        \"RpsTicket\": \"d=" + accessToken + "\""
                        + "    },"
                        + "    \"RelyingParty\": \"http://auth.xboxlive.com\","
                        + "    \"TokenType\": \"JWT\""
                        + "}")
                .asJson();
        // checks for unsuccessful responses
        if (!xbox_response.isSuccess()) {
            throw new IOException("Couldn't get xbox token :" + xbox_response.getStatusText());
        }

        XBLCredentials xblc = new XBLCredentials();
        xblc.token = xbox_response.getBody().getObject().getString("Token");
        xblc.userhash = xbox_response.getBody().getObject().getJSONObject("DisplayClaims").getJSONArray("xui").getJSONObject(0).getString("uhs");
        return xblc;
    }

    private static String getXSTSToken(XBLCredentials xblc) throws IOException {
        HttpResponse<JsonNode> xbox_response = Unirest.post("https://xsts.auth.xboxlive.com/xsts/authorize")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body("{"
                        + "    \"Properties\": {"
                        + "        \"SandboxId\": \"RETAIL\","
                        + "        \"UserTokens\": ["
                        + "            \"" + xblc.token + "\""
                        + "        ]"
                        + "    },"
                        + "    \"RelyingParty\": \"rp://api.minecraftservices.com/\","
                        + "    \"TokenType\": \"JWT\""
                        + "}")
                .asJson();
        // checks for unsuccessful responses
        if (!xbox_response.isSuccess()) {
            throw new IOException("Couldn't get xbox token :" + xbox_response.getStatusText());
        }

        return xbox_response.getBody().getObject().getString("Token");
    }

    private static String getMinecraftBearerToken(XBLCredentials xblc, String xstsToken) throws IOException {
        HttpResponse<JsonNode> xbox_response = Unirest.post("https://api.minecraftservices.com/authentication/login_with_xbox")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body("{"
                        + "   \"identityToken\" : \"XBL3.0 x=" + xblc.userhash + ";" + xstsToken + "\","
                        + "   \"ensureLegacyEnabled\" : true"
                        + "}")
                .asJson();
        // checks for unsuccessful responses
        if (!xbox_response.isSuccess()) {
            throw new IOException("Couldn't get xbox token :" + xbox_response.getStatusText());
        }

        return xbox_response.getBody().getObject().getString("access_token");
    }

    private static class AccessToken {

        public String accessToken;
        public String refreshToken;
        public String userId;

        public AccessToken(JSONObject object) {
            accessToken = getStringOrNull(object, "access_token");
            refreshToken = getStringOrNull(object, "refresh_token");
            userId = getStringOrNull(object, "user_id");
        }

        public AccessToken(YamlConfiguration yaml) {
            accessToken = yaml.getString("access_token", null);
            refreshToken = yaml.getString("refresh_token", null);
            userId = yaml.getString("user_id", null);
        }

        public void writeToFile(YamlConfiguration yaml) {
            yaml.set("access_token", accessToken);
            yaml.set("refresh_token", refreshToken);
            yaml.set("user_id", userId);
        }

        private String getStringOrNull(JSONObject object, String key) {
            return object.has(key) ? object.getString(key) : null;
        }
    }

    private static class XBLCredentials {

        public String token;
        public String userhash;
    }

}
