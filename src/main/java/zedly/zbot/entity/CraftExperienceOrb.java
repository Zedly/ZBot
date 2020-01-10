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
public class CraftExperienceOrb extends CraftEntity implements ExperienceOrb {

    int xp = 0;
    
    @Override
    public EntityType getType() {
        return EntityType.EXPERIENCE_ORB;
    }
    
    public void setExperienceCount(int xp) {
        this.xp = xp;
    }

    @Override
    public int getExperienceCount() {
        return xp;
    }
    
}
