package zedly.zbot.api.event;

import zedly.zbot.api.inventory.ItemStack;

public class WindowItemsEvent extends Event
{
	private final ItemStack[] slots;
	private final int windowId;

	public WindowItemsEvent(int windowId, ItemStack[] slots)
	{
		this.windowId = windowId;
		this.slots = slots;
	}

	public ItemStack[] getSlots()
	{
		return slots;
	}

	public int getWindowId()
	{
		return windowId;
	}
}