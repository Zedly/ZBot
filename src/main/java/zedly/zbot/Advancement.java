/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Dennis
 */
public class Advancement {
    
    private final String parentID;
    private final AdvancementDisplay advancementDisplay;
    private final HashMap<String, Integer> criteria;
    private final List<List<String>> requirements;
    
    public Advancement(String parentID, AdvancementDisplay advancementDisplay, HashMap<String, Integer> criteria, List<List<String>> requirements) {
        this.parentID = parentID;
        this.advancementDisplay = advancementDisplay;
        this.criteria = criteria;
        this.requirements = requirements;
    }
    
    public String getParentID() {
        return parentID;
    }
    
    public AdvancementDisplay getAdvancementDisplay() {
        return advancementDisplay;
    }
    
    public String[] getCriteria() {
        return criteria.keySet().toArray(new String[0]);
    }
    
    public String[][] getRequirements() {
        String[][] req = new String[requirements.size()][];
        for(int i = 0; i < requirements.size(); i++) {
            req[i] = requirements.get(i).toArray(new String[0]);
        }
        return req;
    }
}
