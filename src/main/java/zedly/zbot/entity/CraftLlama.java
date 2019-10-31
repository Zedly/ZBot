/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftLlama extends CraftChestedHorse implements Llama {

    int strength;
    int carpetColorId;
    int variantId;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(16)) {
            strength = metaMap.get(16).asInt();
        }
        if (metaMap.containsKey(17)) {
            carpetColorId = metaMap.get(17).asInt();
        }
        if (metaMap.containsKey(18)) {
            variantId = metaMap.get(18).asInt();
        }
        return list;
    }

    @Override
    public int getStrength() {
        return strength;
    }

    @Override
    public int getCarperColor() {
        return carpetColorId;
    }

    @Override
    public int getVariant() {
        return variantId;
    }

    public EntityType getType() {
        return EntityType.LLAMA;
    }
    
}
