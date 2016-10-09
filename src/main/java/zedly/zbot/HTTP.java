package zedly.zbot;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HTTP {

    public static HTTPResponse https(String url, String postData) throws IOException {
        URL myurl = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
        con.setConnectTimeout(30000);
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Charset", "UTF-8");
        con.setRequestProperty("Content-length", String.valueOf(postData.length()));
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        con.setDoInput(true);
        DataOutputStream output = new DataOutputStream(con.getOutputStream());
        output.writeBytes(postData);
        output.close();
        return receiveContent(con);
    }

    private static HTTPResponse receiveContent(URLConnection con) throws IOException {
        Map<String, List<String>> headers = con.getHeaderFields();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            DataInputStream input = new DataInputStream(con.getInputStream());
            for (int c = input.read(); c != -1; c = input.read()) {
                bos.write(c);
            }
            input.close();
        } catch (IOException ignored) {
        }
        byte[] content = bos.toByteArray();
        return new HTTPResponse(headers, content);
    }

    public static HTTPResponse http(String url) throws IOException {
        URL myurl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) myurl.openConnection();
        con.setConnectTimeout(30000);
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");
        con.setDoInput(true);
        return receiveContent(con);
    }

    public static HTTPResponse https(String url) throws IOException {
        URL myurl = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
        con.setConnectTimeout(30000);
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");
        con.setDoInput(true);
        return receiveContent(con);
    }

    public static class HTTPResponse {

        private final Map<String, List<String>> headers;
        private final byte[] content;

        public HTTPResponse(Map<String, List<String>> headers, byte[] content) {
            this.headers = headers;
            this.content = content;
        }

        public String getCookieString() {
            String httpCookies = "";
            List<String> cookies = headers.get("Set-Cookie");
            for (int i = 0; i < cookies.size() - 1; i++) {
                httpCookies += cookies.get(i).substring(0, cookies.get(i).indexOf(";")) + "; ";
            }
            httpCookies += cookies.get(cookies.size() - 1).substring(0, cookies.get(cookies.size() - 1).indexOf(";"));
            return httpCookies;
        }

        public Map<String, List<String>> getHeaders() {
            return headers;
        }

        public byte[] getContent() {
            return content;
        }
    }
}
