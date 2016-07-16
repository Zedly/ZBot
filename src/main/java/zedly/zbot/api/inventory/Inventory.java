package zedly.zbot.api.inventory;

public interface Inventory
{
	ItemStack getSlot(int i);

	int getSelectedSlot();

	ItemStack getItemInHand();

	ItemStack getItemOnCursor();

	void clickSlot(int slot, int mode, int action);
}
