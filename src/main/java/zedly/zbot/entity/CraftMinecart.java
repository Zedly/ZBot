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
import zedly.zbot.event.entity.EndermanBlockChangeEvent;
import zedly.zbot.inventory.ItemStack;
import zedly.zbot.inventory.CraftItemStack;

/**
 *
 * @author Dennis
 */
public class CraftMinecart extends CraftObject implements Minecart {

    int shakingPower = 0;
    int shakingDirection = 1;
    float shakingMultiplier = 0;
    int blockInCart = 0;
    int blockYPosition = 6;
    boolean showBlock = false;

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(7)) {
            shakingPower = metaMap.get(7).asInt();
        }
        if (metaMap.containsKey(8)) {
            shakingDirection = metaMap.get(8).asInt();
        }
        if (metaMap.containsKey(9)) {
            shakingMultiplier = metaMap.get(9).asFloat();
        }
        if (metaMap.containsKey(10)) {
            blockInCart = metaMap.get(10).asInt();
        }
        if (metaMap.containsKey(11)) {
            blockYPosition = metaMap.get(11).asInt();
        }
        if (metaMap.containsKey(12)) {
            showBlock = metaMap.get(12).asBoolean();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.MINECART;
    }

    @Override
    public synchronized int getShakingPower() {
        return shakingPower;
    }

    @Override
    public synchronized int getShakeDirection() {
        return shakingDirection;
    }

    @Override
    public synchronized float getShakingMultiplier() {
        return shakingMultiplier;
    }

    @Override
    public synchronized ItemStack getBlock() {
        return null;
    }

    @Override
    public synchronized int getBlockYPosition() {
        return blockYPosition;
    }

    @Override
    public synchronized boolean isBlockVisibile() {
        return showBlock;
    }

}
