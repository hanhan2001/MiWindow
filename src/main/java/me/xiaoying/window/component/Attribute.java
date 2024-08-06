package me.xiaoying.window.component;

import me.xiaoying.window.Color;

import java.awt.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum Attribute implements Cloneable {
    NAME("name", 0, (component, graphics, attributeValue) -> component.name(attributeValue.toString())),
    WIDTH("width", 0, (component, graphics, attributeValue) -> component.width(attributeValue.toString())),
    HEIGHT("height", 0, (component, graphics, attributeValue) -> component.height(attributeValue.toString())),
    COLOR("color", 0, (component, graphics, attributeValue) -> {
        if (attributeValue instanceof Color)
            component.getComponent().setForeground(((Color) attributeValue).toAWTColor());
        else
            component.getComponent().setForeground((java.awt.Color) attributeValue);
    }),
    BACKGROUND_COLOR("background_color", 0, (component, graphics, attributeValue) -> {
        if (attributeValue instanceof me.xiaoying.window.Color) {
            if (graphics == null) {
                component.getComponent().setBackground(((Color) attributeValue).toAWTColor());
                return;
            }

            graphics.setColor(((Color) attributeValue).toAWTColor());
            return;
        }

        if (graphics == null) {
            component.getComponent().setBackground((java.awt.Color) attributeValue);
            return;
        }

        graphics.setColor((java.awt.Color) attributeValue);
    }),
    FONT_SIZE("font_size", 0, (component, graphics, attributeValue) -> {}),
    FONT_FAMILY("font_family", 0, (component, graphics, attributeValue) -> {}),
    POSITION("position", 0, (component, graphics, attributeValue) -> {
        if (attributeValue.toString().equalsIgnoreCase("static")) {

        }
        if (attributeValue.toString().equalsIgnoreCase("relative")) {

        }
        if (attributeValue.toString().equalsIgnoreCase("absolute")) {

        }
        if (attributeValue.toString().equalsIgnoreCase("fixed")) {

        }
    }),
    LEFT("left", 0, (component, graphics, attributeValue) -> {}),
    RIGHT("right", 0, (component, graphics, attributeValue) -> {}),
    TOP("top", 0, (component, graphics, attributeValue) -> {
//        double calculator = component.calculator(attributeValue.toString(), "ENDWAYS", false);
//        component.getComponent().setLocation((int) component.getComponent().getLocation().getX(), (int) calculator);
    }),
    BOTTOM("bottom", 0, (component, graphics, attributeValue) -> {}),
    DISPLAY("display", 0, (component, graphics, attributeValue) -> {
        String type = attributeValue.toString().toUpperCase(Locale.ENGLISH);
        if ("NULL".equalsIgnoreCase(type)) {
            component.visible(false);
            return;
        }

        // determine component is Container
        if (!(component instanceof Container))
            return;

        Container container = (Container) component;
        if ("BLOCK".equalsIgnoreCase(type)) {
            for (int i = 0; i < container.getChildren().size(); i++) {
                if (i == 0) {
                    container.getChildren().get(i).getComponent().setLocation((int) container.getComponent().getLocation().getX(), (int) container.getComponent().getLocation().getY());
                    continue;
                }

                Component last = container.getChildren().get(i - 1);
                container.getChildren().get(i).getComponent().setLocation((int) container.getComponent().getLocation().getX(), (int) (last.getComponent().getLocation().getY() + last.getComponent().getHeight()));
            }
            return;
        }

        if ("FLEX".equalsIgnoreCase(type)) {
            for (int i = 0; i < container.getChildren().size(); i++) {
                if (i == 0) {
                    container.getChildren().get(i).getComponent().setLocation((int) container.getComponent().getLocation().getX(), (int) container.getComponent().getLocation().getY());
                    continue;
                }

                Component last = container.getChildren().get(i - 1);
                container.getChildren().get(i).getComponent().setLocation((int) last.getComponent().getLocation().getX() + last.getComponent().getWidth(), (int) (component.getComponent().getLocation().getY()));
            }
        }
    }),
    TEXT("text", 0, (component, graphics, attributeValue) -> component.text(attributeValue.toString()));

    private final String name;
    private final int priority;
    private final AttributeHandle attributeHandle;

    Attribute(String name, int priority, AttributeHandle attributeHandle) {
        this.name = name;
        this.priority = priority;
        this.attributeHandle = attributeHandle;
    }

    public String getName() {
        return this.name;
    }

    public int getPriority() {
        return this.priority;
    }

    public static Map<Attribute, Object> getDefaultAttribute() {
        Map<Attribute, Object> map = new HashMap<>();
        map.put(NAME, null);
        map.put(WIDTH, 0);
        map.put(HEIGHT, 0);
        map.put(COLOR, Color.BLACK);
        map.put(BACKGROUND_COLOR, Color.WHITE);
        map.put(FONT_SIZE, 12);
        map.put(FONT_FAMILY, "Microsoft YaHei");
        map.put(POSITION, "static");
        map.put(LEFT, 0);
        map.put(RIGHT, 0);
        map.put(TOP, 0);
        map.put(BOTTOM, 0);
        map.put(DISPLAY, "block");
        map.put(TEXT, "");
        return map;
    }

    public void run(Component component, Graphics graphics, Object attributeValue) {
        this.attributeHandle.run(component, graphics, attributeValue);
    }

    @Override
    public String toString() {
        return this.getName();
    }
}