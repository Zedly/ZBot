/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zedly.zbot;

/**
 *
 * @author Dennis
 */
public class CraftPlayerProperty implements PlayerProperty {
    
    private final String name;
    private final String value;
    private final boolean isSigned;
    private final String signature;
    
    
    public CraftPlayerProperty(String name, String value, boolean isSigned, String signature) {
        this.name = name;
        this.value = value;
        this.isSigned = isSigned;
        this.signature = signature;
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @return the value
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * @return the isSigned
     */
    @Override
    public boolean isIsSigned() {
        return isSigned;
    }

    /**
     * @return the signature
     */
    @Override
    public String getSignature() {
        return signature;
    }    
}
