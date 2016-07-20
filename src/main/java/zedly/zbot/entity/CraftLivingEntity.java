/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.entity.LivingEntity;
import zedly.zbot.event.Event;
import zedly.zbot.event.entity.EntityHealthChangeEvent;
import zedly.zbot.event.entity.EntityPotionEffectEvent;

/**
 *
 * @author Dennis
 */
public abstract class CraftLivingEntity extends CraftEntity implements LivingEntity {

    protected boolean handActive = true;
    protected boolean leftHandActive = false;
    protected float health = 0;
    protected int potionEffectColor = 0;
    protected boolean potionEffectAmbient = false;
    protected int arrowsStuck = 0;

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
        return list;
    }

    @Override
    public boolean isHandActive() {
        return handActive;
    }

    @Override
    public boolean isLeftHandActive() {
        return leftHandActive;
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public int getPotionEffectColor() {
        return potionEffectColor;
    }

    @Override
    public boolean isPotionEffectAmbient() {
        return potionEffectAmbient;
    }

    @Override
    public int getArrowsStuck() {
        return arrowsStuck;
    }

}
