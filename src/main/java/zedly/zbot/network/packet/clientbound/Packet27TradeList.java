package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import java.util.ArrayList;
import zedly.zbot.inventory.ItemStack;
import zedly.zbot.inventory.Trade;
import zedly.zbot.inventory.ZTrade;
import zedly.zbot.network.ExtendedDataInputStream;

public class Packet27TradeList implements ClientBoundPacket {

    ArrayList<Trade> trades = new ArrayList<>();

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        int windowId = dis.readVarInt();
        int size = dis.readByte();

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

            Trade trade = new ZTrade(input1, input2, output, enabled, numTradeUses, maxTradeUses, xpReward, specialPrice, priceMultiplier, demand);
            trades.add(trade);
        }

    }

}
