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
        if (this.attributes.get(attribute) == null || object == null) {
            this.attributes.put(attribute, object);
            this.component.recalculate();
            return;
        }

        this.lastModel = this.component.getStateManager().getModel();
        this.attributes.put(attribute, object);
    }

    /**
     * Copy attribute for last state
     */
    public void copy() {
        if (this.component.getStateManager().getModel() != StateManager.Model.NORMAL && this.normalAttributes.isEmpty())
            this.normalAttributes.putAll(this.attributes);

        if ((this.lastModel != this.component.getStateManager().getModel() && this.component.getStateManager().getModel() != StateManager.Model.NORMAL) ||
                (this.component.getStateManager().getModel() == StateManager.Model.NORMAL && this.lastModel != StateManager.Model.NORMAL)) {
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
                    } else {
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
                case POSITION: {
                    if (this.get(Attribute.POSITION).toString().equalsIgnoreCase("static")) {

                    }
                    if (this.get(Attribute.POSITION).toString().equalsIgnoreCase("relative")) {

                    }
                    if (this.get(Attribute.POSITION).toString().equalsIgnoreCase("absolute")) {

                    }
                    if (this.get(Attribute.POSITION).toString().equalsIgnoreCase("fixed")) {

                    }
                    break;
                }
                case DISPLAY: {
                    if (this.get(Attribute.DISPLAY).toString().equalsIgnoreCase("null"))
                        this.component.visible(false);

                    if (!(this.component instanceof Container))
                        return;

                    Container container = (Container) this.component;
                    if (this.get(Attribute.DISPLAY).toString().equalsIgnoreCase("block")) {
                        System.out.println(this.component.getAttributes().get(Attribute.NAME));
                        System.out.println(1235);
                        for (int i = 0; i < container.getChildren().size(); i++) {
                            if (i == 0) {
                                container.getChildren().get(i).getComponent().setLocation(0, 0);
                                continue;
                            }

                            Component last = container.getChildren().get(i - 1);
                            container.getChildren().get(i).getComponent().setLocation(0, (int) (last.getComponent().getLocation().getY() + last.getComponent().getHeight()));
                        }
                    }
//                    if (this.get(Attribute.DISPLAY).toString().equalsIgnoreCase("flex")) {
//
//                    }
                    break;
                }
                case TEXT: {
                    this.component.text(this.attributes.get(Attribute.TEXT).toString());
                    break;
                }
            }
        });
    }
}