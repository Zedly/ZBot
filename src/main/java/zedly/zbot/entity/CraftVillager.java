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
import zedly.zbot.entity.Villager;
import zedly.zbot.event.Event;

/**
 *
 * @author Dennis
 */
public class CraftVillager extends CraftAbstractMerchant implements Villager {

    protected VillagerProfession type;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(13)) {
            type = VillagerProfession.getById(metaMap.get(13).asInt());
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.VILLAGER;
    }

    @Override
    public synchronized VillagerProfession getProfession() {
        return type;
    }

}
