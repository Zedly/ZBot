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
public class CraftArmorStand extends CraftObject implements ArmorStand {

    protected boolean handActive = true;
    protected boolean leftHandActive = false;
    protected float health = 0;
    protected int potionEffectColor = 0;
    protected boolean potionEffectAmbient = false;
    protected int arrowsStuck = 0;
    
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

    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        
        if (metaMap.containsKey(6)) {
            int d = metaMap.get(6).asInt();
            handActive = (d & 0x01) != 0;
            leftHandActive = (d & 0x02) != 0;
        }
        if (metaMap.containsKey(7)) {
            float nHealth = metaMap.get(7).asFloat();
            list.add(new EntityHealthChangeEvent(this, health, nHealth));
            health = nHealth;
        }
        if (metaMap.containsKey(8)) {
            potionEffectColor = metaMap.get(8).asInt();
            list.add(new EntityPotionEffectEvent(this, potionEffectColor));
        }
        if (metaMap.containsKey(9)) {
            potionEffectAmbient = metaMap.get(9).asBoolean();
        }
        if (metaMap.containsKey(10)) {
            arrowsStuck = metaMap.get(10).asInt();
        }
        
        if (metaMap.containsKey(11)) {
            int d = metaMap.get(11).asInt();
            small = (d & 0x01) != 0;
            gravity = (d & 0x02) != 0;
            arms = (d & 0x04) != 0;
            basePlate = (d & 0x08) == 0;
            setMarker = (d & 0x10) != 0;
        }
        if (metaMap.containsKey(12)) {
            headRotation = metaMap.get(12).asVector();
        }
        if (metaMap.containsKey(13)) {
            bodyRotation = metaMap.get(13).asVector();
        }
        if (metaMap.containsKey(14)) {
            leftArmRotation = metaMap.get(14).asVector();
        }
        if (metaMap.containsKey(15)) {
            rightArmRotation = metaMap.get(15).asVector();
        }
        if (metaMap.containsKey(16)) {
            leftLegRotation = metaMap.get(16).asVector();
        }
        if (metaMap.containsKey(17)) {
            rightLegRotation = metaMap.get(17).asVector();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.ARMOR_STAND;
    }

    @Override
    public boolean isSmall() {
        return small;
    }

    @Override
    public boolean hasGravity() {
        return gravity;
    }

    @Override
    public boolean hasArms() {
        return arms;
    }

    @Override
    public boolean hasBasePlate() {
        return basePlate;
    }

    @Override
    public boolean hasMarker() {
        return basePlate;
    }

    @Override
    public float[] getHeadRotation() {
        return new float[]{headRotation[0], headRotation[1], headRotation[2]};
    }

    @Override
    public float[] getBodyRotation() {
        return new float[]{bodyRotation[0], bodyRotation[1], bodyRotation[2]};
    }

    @Override
    public float[] getLeftArmRotation() {
        return new float[]{leftArmRotation[0], leftArmRotation[1], leftArmRotation[2]};
    }

    @Override
    public float[] getRightArmRotation() {
        return new float[]{rightArmRotation[0], rightArmRotation[1], rightArmRotation[2]};
    }

    @Override
    public float[] getLeftLegRotation() {
        return new float[]{leftLegRotation[0], leftLegRotation[1], leftLegRotation[2]};
    }

    @Override
    public float[] getRightLegRotation() {
        return new float[]{rightLegRotation[0], rightLegRotation[1], rightLegRotation[2]};
    }

    @Override
    public boolean isLeftHandActive() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isHandActive() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float getHealth() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getPotionEffectColor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isPotionEffectAmbient() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getArrowsStuck() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
