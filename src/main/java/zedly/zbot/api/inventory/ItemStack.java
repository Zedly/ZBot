package zedly.zbot.api.inventory;

import zedly.zbot.api.block.Material;
import net.minecraft.server.NBTBase;

public interface ItemStack {

    Material getType();

    int getTypeId();

    short getDamageValue();

    NBTBase getNbt();

    byte getAmount();
}
