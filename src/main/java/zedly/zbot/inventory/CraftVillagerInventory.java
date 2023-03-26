/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.inventory;

import java.util.ArrayList;
import java.util.List;
import zedly.zbot.GameContext;
import zedly.zbot.network.packet.serverbound.Packet26SelectTrade;

/**
 *
 * @author Dennis
 */
public class CraftVillagerInventory extends CraftExternalInventory implements VillagerInventory {

    private ArrayList<CraftTrade> trades;
    int villagerLevel;
    int experience;
    boolean regular;
    boolean restock;
    
    
    public CraftVillagerInventory(GameContext context, int windowId, String title) {
        super(context, 39, 3, windowId, title);
    }

    @Override
    public void setProperty(int property, int value) {
        // Villager windows have no properties
    }
    
    public void loadVillagerWindow(ArrayList<CraftTrade> trades, int villagerLevel, int experience, boolean regular, boolean restock) {
        this.trades = trades;
        this.villagerLevel = villagerLevel;
        this.experience = experience;
        this.regular = regular;
        this.restock = restock;
    }

    @Override
    public int getNumTrades() {
        return trades.size();
    }
    
    @Override
    public Trade getTrade(int i) {
        return trades.get(i);
    }

    @Override
    public int getVillagerLevel() {
        return villagerLevel;
    }

    @Override
    public int getExperience() {
        return experience;
    }

    @Override
    public boolean isRegular() {
        return regular;
    }

    @Override
    public boolean canRestock() {
        return restock;
    }

    @Override
    public void selectTrade(int i) {
        context.getUpThread().sendPacket(new Packet26SelectTrade(i));
    }
    
}
