/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.Direction;
import zedly.zbot.EntityType;
import zedly.zbot.api.block.Block;
import zedly.zbot.api.entity.Minecart;
import zedly.zbot.api.event.Event;
import zedly.zbot.api.inventory.ItemStack;
import zedly.zbot.inventory.CraftItemStack;

/**
 *
 * @author Dennis
 */
public class CraftMinecart extends CraftObject implements Minecart {

    int shakingPower;
    int shakingDirection;
    float shakingMultiplier;
    ItemStack blockInCart;
    int blockYPosition;
    boolean showBlock;

    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(6)) {
            shakingPower = metaMap.get(6).asInt();
        }
        if (metaMap.containsKey(7)) {
            shakingDirection = metaMap.get(7).asInt();
        }
        if (metaMap.containsKey(8)) {
            shakingMultiplier = metaMap.get(8).asFloat();
        }
        if (metaMap.containsKey(9)) {
            int d = metaMap.get(9).asInt();
            blockInCart = new CraftItemStack(d >> 4, d & 0xF);
        }
        if (metaMap.containsKey(10)) {
            blockYPosition = metaMap.get(10).asInt();
        }
        if (metaMap.containsKey(11)) {
            showBlock = metaMap.get(11).asBoolean();
        }
        return list;
    }

    @Override
    public EntityType getType() {
        return EntityType.MINECART;
    }

    @Override
    public int getShakingPower() {
        return shakingPower;
    }

    @Override
    public int getShakeDirection() {
        return shakingDirection;
    }

    @Override
    public float getShakingMultiplier() {
        return shakingMultiplier;
    }

    @Override
    public ItemStack getBlock() {
        return blockInCart;
    }

    @Override
    public int getBlockYPosition() {
        return blockYPosition;
    }

    @Override
    public boolean isBlockVisibile() {
        return showBlock;
    }

}
