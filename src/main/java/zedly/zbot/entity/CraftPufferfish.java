/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import zedly.zbot.EntityType;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftPufferfish extends CraftAbstractFish implements Pufferfish {

    int puffState;
    
    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(15)) {
            puffState = metaMap.get(15).asInt();
        }
        return list;
    }
    
    @Override
    public EntityType getType() {
        return EntityType.PUFFERFISH;
    }

    @Override
    public int getPuffState() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
