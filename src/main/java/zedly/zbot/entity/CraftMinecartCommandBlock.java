/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.MinecartCommandBlock;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftMinecartCommandBlock extends CraftMinecart implements MinecartCommandBlock {

    protected String command, lastOutput;
    
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(14)) {
            command = metaMap.get(14).asString();
        }
        if (metaMap.containsKey(15)) {
            lastOutput = metaMap.get(15).asString();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.MINECART;
    }

    @Override
    public synchronized String getLastOutput() {
        return lastOutput;
    }

    @Override
    public synchronized String getCommand() {
        return command;
    }
    
}
