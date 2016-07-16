package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.api.entity.Potion;
import zedly.zbot.api.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import zedly.zbot.api.event.Event;
import zedly.zbot.inventory.CraftItemStack;

public class CraftPotion extends CraftProjectile implements Potion {

    protected CraftItemStack itemStack;

    @Override
    public EntityType getType() {
        return EntityType.POTION;
    }

    @Override
    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = super.setMeta(metaMap);
        if (metaMap.containsKey(6)) {
            itemStack = metaMap.get(6).asSlot();
        }
        return list;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }
    
    public boolean hasGravity() {
        return true;
    }
}
