/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.entity.ArrowSpectral;

/**
 *
 * @author Dennis
 */
public class CraftArrowSpectral extends CraftArrow implements ArrowSpectral {

    @Override
    public EntityType getType() {
        return EntityType.SPECTRAL_ARROW;
    }

    @Override
    public boolean hasGravity() {
        return true;
    }
    
}
