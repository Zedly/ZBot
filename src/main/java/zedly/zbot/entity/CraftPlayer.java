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
import net.minecraft.server.NBTBase;
import zedly.zbot.event.Event;

public class CraftPlayer extends CraftLivingEntity implements Player {

    protected String name = "";
    protected UUID uuid;
    protected float additionalHearts = 0;
    protected boolean leftHanded = false;
    protected int score = 0;
    protected int skinFlags = 0;
    NBTBase leftShoulderEntity;
    NBTBase rightShoulderEntity;
    
    
    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);       
        if (metaMap.containsKey(14)) {
            additionalHearts = metaMap.get(14).asFloat();
        }
        if (metaMap.containsKey(15)) {
            score = metaMap.get(15).asInt();
        }
        if (metaMap.containsKey(16)) {
            skinFlags = metaMap.get(16).asInt();
        }
        if (metaMap.containsKey(17)) {
            leftHanded = metaMap.get(17).asInt() == 0;
        }
        if (metaMap.containsKey(18)) {
            leftShoulderEntity = metaMap.get(18).asNBT();
        }
        if (metaMap.containsKey(19)) {
            rightShoulderEntity = metaMap.get(19).asNBT();
        }
        return list;
    }

    @Override
    public synchronized String getCustomName() {
        return name;
    }

    @Override
    public synchronized UUID getUUID() {
        return uuid;
    }
    
    public synchronized void setUUID(UUID uuid) {
        this.uuid = uuid;
    }
    
    public synchronized void setName(String name) {
        this.name = name;
    }

    @Override
    public synchronized String getName() {
        return name;
    }
    
    @Override
    public EntityType getType() {
        return EntityType.PLAYER;
    }

    @Override
    public synchronized float getAdditionalHearts() {
        return additionalHearts;
    }

    @Override
    public synchronized int getScore() {
        return score;
    }

    @Override
    public synchronized int getSkinFlags() {
        return skinFlags;
    }

    @Override
    public synchronized boolean isLeftHanded() {
        return leftHanded;
    }

    @Override
    public NBTBase getLeftShoulderEntity() {
        return leftShoulderEntity;
    }

    @Override
    public NBTBase getRightShoulderEntity() {
        return rightShoulderEntity;
    }
}
