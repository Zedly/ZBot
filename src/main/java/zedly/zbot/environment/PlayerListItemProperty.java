/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.environment;

/**
 *
 * @author Dennis
 */
public class PlayerListItemProperty {
    public String name;
    public String value;
    public boolean signed;
    public String signature;
    
    public PlayerListItemProperty(String name, String value) {
        this.name = name;
        this.value = value;
        this.signed = false;
    }
    
    public PlayerListItemProperty(String name, String value, String signature) {
        this.name = name;
        this.value = value;
        this.signed = true;
        this.signature = signature;
    }
}
