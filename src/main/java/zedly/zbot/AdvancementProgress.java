/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot;

import java.util.HashMap;

/**
 *
 * @author Dennis
 */
public class AdvancementProgress {

    private final HashMap<String, Long> progresses;

    public AdvancementProgress(HashMap<String, Long> progresses) {
        this.progresses = progresses;
    }

    public long getProgress(String name) {
        return progresses.get(name);
    }

    public boolean hasProgress(String name) {
        return progresses.containsKey(name);
    }
}
