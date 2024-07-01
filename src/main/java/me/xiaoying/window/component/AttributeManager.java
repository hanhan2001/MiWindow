package me.xiaoying.window.component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Component attribute manager
 */
public class AttributeManager {
    private final Component component;
    private final Map<Attribute, Object> attributes = Attribute.getDefaultAttribute();

    public AttributeManager(Component component) {
        this.component = component;
    }

    /**
     * Set attribute for component
     *
     * @param attribute Attribute
     * @param object Value
     */
    public void set(Attribute attribute, Object object) {
        System.out.println("--------------");
        System.out.println("Attr: " + attribute.getName());
        System.out.println("Value: " + object);
        if (this.attributes.get(attribute) == null || object == null) {
            this.attributes.put(attribute, object);
            this.component.recalculate();
            return;
        }

        if (this.attributes.get(attribute).toString().equalsIgnoreCase(object.toString())) {
            System.out.println(12356);
        }

        this.attributes.put(attribute, object);
//        this.component.recalculate();
    }

    /**
     * Get attribute for component
     *
     * @param attribute Attribute
     * @return Object
     */
    public Object get(Attribute attribute) {
        return this.attributes.get(attribute);
    }

    /**
     * Get all attribute for component
     *
     * @return Map (String, Object)
     */
    public Map<String, Object> getAttributes() {
        Map<String, Object> map = new HashMap<>();
        this.attributes.forEach((attribute, object) -> map.put(attribute.getName(), object));
        return map;
    }
}