/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;

/**
 *
 * @author Dennis
 */
public class CraftWanderingTrader extends CraftAbstractMerchant implements WanderingTrader {

    @Override
    public EntityType getType() {
        return EntityType.WANDERING_TRADER;
    }
    
}
