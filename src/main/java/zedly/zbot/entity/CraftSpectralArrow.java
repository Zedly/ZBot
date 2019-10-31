/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.entity.SpectralArrow;

/**
 *
 * @author Dennis
 */
public class CraftSpectralArrow extends CraftArrow implements SpectralArrow {

    @Override
    public synchronized EntityType getType() {
        return EntityType.SPECTRAL_ARROW;
    }

    @Override
    public boolean hasGravity() {
        return true;
    }
    
}
