package zedly.zbot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    private static final JsonParser jsonParser;

    static {
        jsonParser = new JsonParser();
    }

    public static String interpretJson(String json) {
        try {
            JsonElement element = jsonParser.parse(json);
            JsonObject obj = element.getAsJsonObject();
            String result = "";
            if (obj.has("extra")) {
                JsonArray array = obj.getAsJsonArray("extra");
                for (JsonElement e : array) {
                    if (e.isJsonObject()) {
                        JsonObject o = e.getAsJsonObject();
                        result += o.get("text").getAsString();
                    } else {
                        result += e.getAsString();
                    }
                }
            }
            if (obj.has("text")) {
                result += obj.get("text").getAsString();
            }
            return unescape(result);
        } catch (IllegalStateException ex) {
            return json.replaceAll("\u00A7[0-9a-fklmnor]", "");
        }
    }

    public static String unescape(String string) {
        Matcher m = Pattern.compile("[^\\\\]\\\\u[0-9a-f]{4}").matcher(string);
        HashSet<String> _s = new HashSet<>();
        while (m.find()) {
            _s.add(m.group());
        }
        for (String s : _s) {
            int argh = Integer.parseInt(s.substring(3), 16);
            string = string.replace(s.substring(1), ((char) argh) + "");
        }
        string = string.replace("\\\"", "\"");
        string = string.replace("\\\\", "\\");
        return string;
    }

    public static int getVarIntLength(int i) {
        int s = 1;
        while ((i >>= 7) != 0) {
            s++;
        }
        return s;
    }

    public static String stringExtract(String text, String start, String end) {
        int left = text.indexOf(start) + start.length();
        if (left == -1) {
            return null;
        }
        int right = text.indexOf(end, left);
        if (right == -1) {
            return null;
        }
        return text.substring(left, right);
    }

    public static String stringExtract(String text, String start, String end, int offset) {
        int left = text.indexOf(start, offset);
        if (left == -1) {
            return null;
        }
        int right = text.indexOf(end, left + start.length());
        if (right == -1) {
            return null;
        }
        return text.substring(left + start.length(), right);
    }

    public static String stringExtractAll(final String text, final String start, final String end) {
        String result = "";
        int offset = 0;
        while (true) {
            int left = text.indexOf(start, offset);
            if (left == -1) {
                return result;
            }
            int right = text.indexOf(end, left + start.length());
            if (right == -1) {
                return result;
            }
            result += text.substring(left + start.length(), right);
            offset = right + end.length();
        }
    }

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 3];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 3] = hexArray[v >>> 4];
            hexChars[j * 3 + 1] = hexArray[v & 0x0F];
            hexChars[j * 3 + 2] = 32;
        }
        return new String(hexChars);
    }
}
