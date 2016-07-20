/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.entity.Chicken;

/**
 *
 * @author Dennis
 */
public class CraftChicken extends CraftAnimal implements Chicken {
    
    @Override
    public EntityType getType() {
        return EntityType.CHICKEN;
    }
    
}
