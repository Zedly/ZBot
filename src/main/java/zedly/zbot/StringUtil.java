/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Dennis
 */
public class StringUtil {

    private static final char[] hexValues = {0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x61, 0x62, 0x63, 0x64, 0x65, 0x66};
    private static final JsonParser jsonParser = new JsonParser();

    
    public static String extract(String text, String start, String end) {
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

    public static String extract(String text, String start, String end, int offset) {
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

    public static String[] extractAll(final String text, final String start, final String end) {
        String result = "";
        LinkedList<String> res_ = new LinkedList<>();
        int offset = 0;
        while (true) {
            int left = text.indexOf(start, offset);
            if (left == -1) {
                String[] res = new String[res_.size()];
                res = res_.toArray(res);
                return res;
            }
            int right = text.indexOf(end, left + start.length());
            if (right == -1) {
                String[] res = new String[res_.size()];
                res = res_.toArray(res);
                return res;
            }
            res_.add(text.substring(left + start.length(), right));
            offset = right + end.length();
        }
    }

    public static String hexString(byte[] binary) {
        char[] hex = new char[binary.length * 2];
        for (int i = 0; i < binary.length; i++) {
            hex[2 * i] = hexValues[(binary[i] >> 4) & 0xF];
            hex[2 * i + 1] = hexValues[binary[i] & 0xF];
        }
        return new String(hex);
    }

    private static int findWrapPoint(String sentence, int offset, int length) {
        if (sentence.length() <= length + offset) {
            return sentence.length();
        }
        int i = length + offset;
        int k = offset;
        while ((k = sentence.indexOf(" ", k + 1)) < length + offset && k != -1) {
            i = k;
        }
        return i;
    }

    public static ArrayList<String> wrap(String sentence, int length) {
        ArrayList<String> lines = new ArrayList<>();
        int k = 0, l;
        while (true) {
            l = findWrapPoint(sentence, k, length);
            lines.add(sentence.substring(k, l));
            if (l == sentence.length()) {
                break;
            }
            k = l + 1;
        }
        return lines;
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
            return json.replaceAll("§[0-9a-fklmnor]", "");
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
