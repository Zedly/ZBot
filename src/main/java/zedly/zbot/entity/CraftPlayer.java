/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.Player;

import java.util.UUID;
import zedly.zbot.event.Event;

public class CraftPlayer extends CraftLivingEntity implements Player {

    protected String name = "";
    protected UUID uuid;
    protected float additionalHearts = 0;
    protected boolean leftHanded = false;
    protected int score = 0;
    protected int skinFlags = 0;
    
    
    @Override
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);       
        if (metaMap.containsKey(11)) {
            additionalHearts = metaMap.get(11).asFloat();
        }
        if (metaMap.containsKey(12)) {
            score = metaMap.get(12).asInt();
        }
        if (metaMap.containsKey(13)) {
            skinFlags = metaMap.get(13).asInt();
        }
        if (metaMap.containsKey(14)) {
            leftHanded = metaMap.get(14).asInt() == 0;
        }
        return list;
    }

    @Override
    public String getCustomName() {
        return name;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }
    
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public EntityType getType() {
        return EntityType.PLAYER;
    }

    @Override
    public float getAdditionalHearts() {
        return additionalHearts;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public int getSkinFlags() {
        return skinFlags;
    }

    @Override
    public boolean isLeftHanded() {
        return leftHanded;
    }
}
