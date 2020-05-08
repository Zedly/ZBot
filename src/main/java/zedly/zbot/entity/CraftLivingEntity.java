/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import zedly.zbot.Location;
import zedly.zbot.PotionEffect;
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
    protected Location bedLocation;
    private final HashMap<PotionEffect, Integer> potionEffects = new HashMap<>();

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(7)) {
            int d = metaMap.get(7).asInt();
            handActive = (d & 0x01) != 0;
            leftHandActive = (d & 0x02) != 0;
        }
        if (metaMap.containsKey(8)) {
            float nHealth = metaMap.get(8).asFloat();
            list.add(new EntityHealthChangeEvent(this, health, nHealth));
            health = nHealth;
        }
        if (metaMap.containsKey(9)) {
            potionEffectColor = metaMap.get(9).asInt();
            list.add(new EntityPotionEffectEvent(this, potionEffectColor));
        }
        if (metaMap.containsKey(10)) {
            potionEffectAmbient = metaMap.get(10).asBoolean();
        }
        if (metaMap.containsKey(11)) {
            arrowsStuck = metaMap.get(11).asInt();
        }
        if (metaMap.containsKey(12)) {
            bedLocation = metaMap.get(12).asLocation();
        }
        return list;
    }

    public void setPotionEffect(PotionEffect effect, int level) {
        if (level <= 0) {
            potionEffects.remove(effect);
        } else {
            this.potionEffects.put(effect, level);
        }
    }

    @Override
    public synchronized boolean isHandActive() {
        return handActive;
    }

    @Override
    public synchronized boolean isLeftHandActive() {
        return leftHandActive;
    }

    @Override
    public synchronized float getHealth() {
        return health;
    }

    @Override
    public synchronized int getPotionEffectColor() {
        return potionEffectColor;
    }

    @Override
    public Map<PotionEffect, Integer> getPotionEffects() {
        return (Map<PotionEffect, Integer>) potionEffects.clone();
    }

    @Override
    public synchronized boolean isPotionEffectAmbient() {
        return potionEffectAmbient;
    }

    @Override
    public synchronized int getArrowsStuck() {
        return arrowsStuck;
    }

    @Override
    public Location getBedLocation() {
        return bedLocation;
    }

}
