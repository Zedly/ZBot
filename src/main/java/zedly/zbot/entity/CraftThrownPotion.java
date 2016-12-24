package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.event.Event;

public class CraftThrownPotion extends CraftProjectile implements ThrownPotion {

    protected ItemStack itemStack;

    @Override
    public EntityType getType() {
        return EntityType.THROWN_POTION;
    }

    @Override
    public synchronized List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(6)) {
            itemStack = metaMap.get(6).asSlot();
        }
        return list;
    }

    @Override
    public synchronized ItemStack getItemStack() {
        return itemStack;
    }
    
    public boolean hasGravity() {
        return true;
    }
}
