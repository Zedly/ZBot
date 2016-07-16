/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.api.entity.Insentient;
import zedly.zbot.api.event.Event;

/**
 *
 * @author Dennis
 */
public abstract class CraftInsentient extends CraftLivingEntity implements Insentient {
    
    private boolean hasAI = true;
    private boolean leftHanded = false;
    
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if(metaMap.containsKey(11)) {
            int d = metaMap.get(11).asInt();
            hasAI = (d & 0x01) == 0;
            leftHanded = (d & 0x02) != 0;
        }
        return list;
    }

    public boolean hasAI() {
        return hasAI;
    }

    public boolean isLeftHanded() {
        return leftHanded;
    }
}
