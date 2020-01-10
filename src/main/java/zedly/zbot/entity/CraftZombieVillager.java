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
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftZombieVillager extends CraftZombie implements ZombieVillager {

    protected int villagerTypeId = 0;
    protected boolean converting = false;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(19)) {
            converting = metaMap.get(19).asBoolean();
        }
        if (metaMap.containsKey(20)) {
            villagerTypeId = metaMap.get(20).asInt();
        }
        return list;
    }

    @Override
    public VillagerProfession getProfession() {
        return VillagerProfession.getById(villagerTypeId);
    }

    @Override
    public boolean isConverting() {
        return converting;
    }
    
    public EntityType getType() {
        return EntityType.ZOMBIE_VILLAGER;
    }

}
