package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import java.util.ArrayList;
import zedly.zbot.GameContext;
import zedly.zbot.event.WindowOpenFinishEvent;
import zedly.zbot.inventory.CraftInventory;
import zedly.zbot.inventory.CraftVillagerInventory;
import zedly.zbot.inventory.ItemStack;
import zedly.zbot.inventory.Trade;
import zedly.zbot.inventory.CraftTrade;
import zedly.zbot.network.ExtendedDataInputStream;

public class Packet27TradeList implements ClientBoundPacket {

    ArrayList<CraftTrade> trades = new ArrayList<>();
    int windowId;
    int size;
    int villagerLevel;
    int experience;
    boolean regular;
    boolean restock;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        windowId = dis.readVarInt();
        size = dis.readByte();

        for (int i = 0; i < size; i++) {
            ItemStack input1 = dis.readSlot();
            ItemStack input2 = null;
            ItemStack output = dis.readSlot();

            if (dis.readBoolean()) {
                input2 = dis.readSlot();
            }
            boolean enabled = !dis.readBoolean();
            int numTradeUses = dis.readInt();
            int maxTradeUses = dis.readInt();
            int xpReward = dis.readInt();
            int specialPrice = dis.readInt();
            double priceMultiplier = dis.readFloat();
            int demand = dis.readInt();

            CraftTrade trade = new CraftTrade(input1, input2, output, enabled, numTradeUses, maxTradeUses, xpReward, specialPrice, priceMultiplier, demand);
            trades.add(trade);
        }
        villagerLevel = dis.readVarInt();
        experience = dis.readVarInt();
        regular = dis.readBoolean();
        restock = dis.readBoolean();
    }
    
    @Override
    public void process(GameContext context) {
        CraftInventory inventory = context.getSelf().getInventory();
        if(inventory.windowId() == windowId && inventory instanceof CraftVillagerInventory) {
            ((CraftVillagerInventory) inventory).loadVillagerWindow(trades, villagerLevel, experience, regular, restock);
            context.getEventDispatcher().dispatchEvent(new WindowOpenFinishEvent(inventory));
        }
    }

}
