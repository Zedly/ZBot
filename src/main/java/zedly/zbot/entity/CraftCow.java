package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.entity.Cow;

public class CraftCow extends CraftAnimal implements Cow {

    @Override
    public EntityType getType() {
        return EntityType.COW;
    }
}
