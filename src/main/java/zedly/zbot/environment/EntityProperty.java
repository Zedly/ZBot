package zedly.zbot.environment;

import zedly.zbot.ListElement;
import java.util.LinkedList;

public class EntityProperty {
    public String key;
    public double value;
    public LinkedList<ListElement> elements;
    
    public EntityProperty(String key, double value, LinkedList<ListElement> elements) {
        this.key = key;
        this.value = value;
        this.elements = elements;
    }
}