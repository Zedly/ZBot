/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.entity.ArmorStand;
import zedly.zbot.event.Event;
import zedly.zbot.event.entity.EntityHealthChangeEvent;
import zedly.zbot.event.entity.EntityPotionEffectEvent;

/**
 *
 * @author Dennis
 */
public class CraftArmorStand extends CraftLivingEntity implements ArmorStand {
    
    protected boolean small = false;
    protected boolean gravity = true;
    protected boolean arms = true;
    protected boolean basePlate = true;
    protected boolean setMarker = false;
    protected float[] headRotation = {0, 0, 0};
    protected float[] bodyRotation = {0, 0, 0};
    protected float[] leftArmRotation = {0, 0, 0};
    protected float[] rightArmRotation = {0, 0, 0};
    protected float[] leftLegRotation = {0, 0, 0};
    protected float[] rightLegRotation = {0, 0, 0};

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        
        if (metaMap.containsKey(13)) {
            int d = metaMap.get(13).asInt();
            small = (d & 0x01) != 0;
            arms = (d & 0x04) != 0;
            basePlate = (d & 0x08) == 0;
            setMarker = (d & 0x10) != 0;
        }
        if (metaMap.containsKey(14)) {
            headRotation = metaMap.get(14).asVector();
        }
        if (metaMap.containsKey(15)) {
            bodyRotation = metaMap.get(15).asVector();
        }
        if (metaMap.containsKey(16)) {
            leftArmRotation = metaMap.get(16).asVector();
        }
        if (metaMap.containsKey(17)) {
            rightArmRotation = metaMap.get(17).asVector();
        }
        if (metaMap.containsKey(18)) {
            leftLegRotation = metaMap.get(18).asVector();
        }
        if (metaMap.containsKey(19)) {
            rightLegRotation = metaMap.get(19).asVector();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.ARMOR_STAND;
    }

    @Override
    public synchronized boolean isSmall() {
        return small;
    }

    @Override
    public synchronized boolean hasGravity() {
        return gravity;
    }

    @Override
    public synchronized boolean hasArms() {
        return arms;
    }

    @Override
    public synchronized boolean hasBasePlate() {
        return basePlate;
    }

    @Override
    public synchronized boolean hasMarker() {
        return basePlate;
    }

    @Override
    public synchronized float[] getHeadRotation() {
        return new float[]{headRotation[0], headRotation[1], headRotation[2]};
    }

    @Override
    public synchronized float[] getBodyRotation() {
        return new float[]{bodyRotation[0], bodyRotation[1], bodyRotation[2]};
    }

    @Override
    public synchronized float[] getLeftArmRotation() {
        return new float[]{leftArmRotation[0], leftArmRotation[1], leftArmRotation[2]};
    }

    @Override
    public synchronized float[] getRightArmRotation() {
        return new float[]{rightArmRotation[0], rightArmRotation[1], rightArmRotation[2]};
    }

    @Override
    public synchronized float[] getLeftLegRotation() {
        return new float[]{leftLegRotation[0], leftLegRotation[1], leftLegRotation[2]};
    }

    @Override
    public synchronized float[] getRightLegRotation() {
        return new float[]{rightLegRotation[0], rightLegRotation[1], rightLegRotation[2]};
    }
}
