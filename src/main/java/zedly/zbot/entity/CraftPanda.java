/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.EntityType;
import zedly.zbot.event.Event;
import zedly.zbot.inventory.ItemStack;

/**
 *
 * @author Dennis
 */
public class CraftPanda extends CraftAnimal implements Panda {

    int breedTimer;
    int sneezeTimer;
    int eatTimer;
    int mainGeneId;
    int hiddenGeneId;
    int flags;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(15)) {
            breedTimer = metaMap.get(15).asInt();
        }
        if (metaMap.containsKey(16)) {
            sneezeTimer = metaMap.get(16).asInt();
        }
        if (metaMap.containsKey(17)) {
            eatTimer = metaMap.get(17).asInt();
        }
        if (metaMap.containsKey(18)) {
            mainGeneId = metaMap.get(18).asInt();
        }
        if (metaMap.containsKey(19)) {
            hiddenGeneId = metaMap.get(19).asInt();
        }
        if (metaMap.containsKey(20)) {
            flags = metaMap.get(20).asInt();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.PANDA;
    }

    @Override
    public int getBreedTimer() {
        return breedTimer;
    }

    @Override
    public int getSneezeTimer() {
        return sneezeTimer;
    }

    @Override
    public int getEatingTimer() {
        return eatTimer;
    }

    @Override
    public int getMainGeneId() {
        return mainGeneId;
    }

    @Override
    public int getHiddenGeneId() {
        return hiddenGeneId;
    }

    @Override
    public boolean isSneezing() {
        return (flags & 0x2) != 0;
    }

    @Override
    public boolean isEating() {
      return (flags & 0x4) != 0;
    }

}
