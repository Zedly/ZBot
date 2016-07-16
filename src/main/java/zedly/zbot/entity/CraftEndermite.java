/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.api.entity.Endermite;

/**
 *
 * @author Dennis
 */
public class CraftEndermite extends CraftMonster implements Endermite {

    @Override
    public EntityType getType() {
        return EntityType.ENDERMITE;
    }
    
}
