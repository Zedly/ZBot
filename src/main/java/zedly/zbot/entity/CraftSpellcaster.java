/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public abstract class CraftSpellcaster extends CraftAbstractIllager implements SpellcasterIllager {
    
    protected int spellState;
    
    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(15)) {
            spellState = metaMap.get(15).asInt();
        }
        return list;
    }
    
    @Override
    public int getSpellState() {
        return spellState;
    }
    
}
