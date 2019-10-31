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
        if (metaMap.containsKey(18)) {
            strength = metaMap.get(18).asInt();
        }
        if (metaMap.containsKey(19)) {
            carpetColorId = metaMap.get(19).asInt();
        }
        if (metaMap.containsKey(20)) {
            variantId = metaMap.get(20).asInt();
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
