package me.xiaoying.window.component;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Component attribute manager
 */
public class AttributeManager {
    private final Component component;
    private Map<Attribute, Object> attributes = Attribute.getDefaultAttribute();
    private Map<Attribute, Object> normalAttributes = new HashMap<>();
    private StateManager.Model lastModel = StateManager.Model.NORMAL;

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
        System.out.println("Normal: " + this.normalAttributes.get(Attribute.BACKGROUND_COLOR));

        if (this.attributes.get(attribute) == null || object == null) {
            this.attributes.put(attribute, object);
            this.component.recalculate();
            return;
        }

        if (this.attributes.get(attribute).toString().equalsIgnoreCase(object.toString())) {
//            System.out.println(12356);
        }

        this.lastModel = this.component.getStateManager().getModel();
        this.attributes.put(attribute, object);
    }

    public void copy() {
        if (this.component.getStateManager().getModel() != StateManager.Model.NORMAL && this.normalAttributes.isEmpty()) {
            this.normalAttributes.putAll(this.attributes);
        }

        if (this.component.getStateManager().getModel() == StateManager.Model.NORMAL && this.lastModel != StateManager.Model.NORMAL) {
            this.attributes.clear();
            this.attributes.putAll(this.normalAttributes);
        }
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

    public void repaint() {
        this.repaint(null);
    }

    public void repaint(Graphics graphics) {
        this.attributes.forEach((key, attribute) -> {
            if (attribute == null)
                return;

            switch (key) {
                case NAME: {
                    this.component.name(attribute.toString());
                    break;
                }
                case WIDTH: {
                    this.component.width(attribute.toString());
                    break;
                }
                case HEIGHT: {
                    this.component.height(attribute.toString());
                    break;
                }
                case BACKGROUND_COLOR: {
                    if (attribute instanceof me.xiaoying.window.Color) {
                        if (graphics == null) {
                            this.component.getComponent().setBackground(((me.xiaoying.window.Color) attribute).toAWTColor());
                            return;
                        }

                        graphics.setColor(((me.xiaoying.window.Color) attribute).toAWTColor());
                    }
                    else {
                        if (graphics == null) {
                            this.component.getComponent().setBackground((Color) attribute);
                            return;
                        }

                        graphics.setColor((Color) attribute);
                    }
                    break;
                }
                case COLOR: {
                    if (attribute instanceof me.xiaoying.window.Color)
                        this.component.getComponent().setForeground(((me.xiaoying.window.Color) attribute).toAWTColor());
                    else
                        this.component.getComponent().setForeground((Color) attribute);
                    break;
                }
            }
        });
    }
}