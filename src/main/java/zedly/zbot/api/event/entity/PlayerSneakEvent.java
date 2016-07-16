/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.api.event.entity;

import zedly.zbot.api.entity.Player;
import zedly.zbot.api.event.Event;

/**
 *
 * @author Dennis
 */
public class PlayerSneakEvent extends Event {
    
    private final Player player;
    private final boolean sneaking;
    
    public PlayerSneakEvent(Player player, boolean sneaking) {
        this.player = player;
        this.sneaking = sneaking;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isSneaking() {
        return sneaking;
    }
    
}
