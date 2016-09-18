/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.VillagerProfession;
import zedly.zbot.entity.Zombie;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftZombie extends CraftMonster implements Zombie {

    protected boolean baby = false;
    protected VillagerProfession profession = null;
    protected boolean converting = false;
    protected boolean holdingHandsUp = false;

    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(12)) {
            baby = metaMap.get(12).asBoolean();
        }
        if (metaMap.containsKey(13)) {
            int p = metaMap.get(13).asInt();
            if (p == 0) {
                profession = null;
            } else {
                profession = VillagerProfession.getById(p);
            }
        }
        if (metaMap.containsKey(14)) {
            converting = metaMap.get(14).asBoolean();
        }
        if (metaMap.containsKey(15)) {
            holdingHandsUp = metaMap.get(15).asBoolean();
        }
        return list;
    }

    public EntityType getType() {
        return EntityType.ZOMBIE;
    }

    @Override
    public synchronized boolean isBaby() {
        return baby;
    }

    @Override
    public synchronized boolean isVillager() {
        return profession != null;
    }

    @Override
    public synchronized VillagerProfession getProfession() {
        return profession;
    }

    @Override
    public synchronized boolean isConverting() {
        return converting;
    }

    @Override
    public synchronized boolean isHoldingHandsUp() {
        return holdingHandsUp;
    }

}
