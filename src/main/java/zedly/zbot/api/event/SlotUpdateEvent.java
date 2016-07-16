package zedly.zbot.api.event;

import zedly.zbot.api.inventory.ItemStack;

public class SlotUpdateEvent extends Event
{
	private final ItemStack itemStack;
	private final short slotId;

	public SlotUpdateEvent(short slotId, ItemStack is)
	{
		this.slotId = slotId;
		this.itemStack = is;
	}

	public ItemStack getItemStack()
	{
		return itemStack;
	}

	public short getSlotId()
	{
		return slotId;
	}
}