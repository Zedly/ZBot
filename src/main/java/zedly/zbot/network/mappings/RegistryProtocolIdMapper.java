/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.mappings;

import com.google.common.io.Resources;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.function.BiConsumer;
import org.apache.commons.io.Charsets;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import zedly.zbot.EntityType;

/**
 *
 * @author Dennis
 */
public class RegistryProtocolIdMapper {

    public static void mapFlatRegistry(String registryName, BiConsumer<String, Integer> mappingConsumer) throws IOException, ParseException {
        URL url = Resources.getResource("registries.json");
        String s_json = Resources.toString(url, Charsets.UTF_8);
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(s_json);
        JSONObject entityTypesJson = (JSONObject) json.get(registryName);
        JSONObject entityTypesEntries = (JSONObject) entityTypesJson.get("entries");
        for (java.lang.Object o : entityTypesEntries.entrySet()) {
            if (o instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) o;
                java.lang.Object key = entry.getKey();
                java.lang.Object value = entry.getValue();
                if (key instanceof String && value instanceof JSONObject) {
                    String namespacedKey = (String) key;
                    JSONObject j_value = (JSONObject) value;
                    int protocolId = (int) (long) (Long) j_value.get("protocol_id");
                    mappingConsumer.accept(namespacedKey, protocolId);
                }
            }
        }
    }

}
