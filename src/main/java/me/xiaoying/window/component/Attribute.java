package me.xiaoying.window.component;

import me.xiaoying.window.Color;

import java.awt.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum Attribute implements Cloneable {
    NAME("name", (component, graphics, attributeValue) -> component.name(attributeValue.toString())),
    WIDTH("width", (component, graphics, attributeValue) -> component.width(attributeValue.toString())),
    HEIGHT("height", ((component, graphics, attributeValue) -> component.height(attributeValue.toString()))),
    COLOR("color", ((component, graphics, attributeValue) -> {
        if (attributeValue instanceof Color)
            component.getComponent().setForeground(((Color) attributeValue).toAWTColor());
        else
            component.getComponent().setForeground((java.awt.Color) attributeValue);
    })),
    BACKGROUND_COLOR("background_color", ((component, graphics, attributeValue) -> {
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
    })),
    FONT_SIZE("font_size", ((component, graphics, attributeValue) -> {})),
    FONT_FAMILY("font_family", ((component, graphics, attributeValue) -> {})),
    POSITION("position", ((component, graphics, attributeValue) -> {
        if (attributeValue.toString().equalsIgnoreCase("static")) {

        }
        if (attributeValue.toString().equalsIgnoreCase("relative")) {

        }
        if (attributeValue.toString().equalsIgnoreCase("absolute")) {

        }
        if (attributeValue.toString().equalsIgnoreCase("fixed")) {

        }
    })),
    LEFT("left", ((component, graphics, attributeValue) -> {})),
    RIGHT("right", ((component, graphics, attributeValue) -> {})),
    TOP("top", ((component, graphics, attributeValue) -> {})),
    BOTTOM("bottom", ((component, graphics, attributeValue) -> {})),
    DISPLAY("display", ((component, graphics, attributeValue) -> {
        String type = attributeValue.toString();
        switch (type.toUpperCase(Locale.ENGLISH)) {
            case "NULL":
                component.visible(false);
                break;
            case "BLOCK": {
                if (!(component instanceof Container))
                    break;

                Container container = (Container) component;
                for (int i = 0; i < container.getChildren().size(); i++) {
                    if (i == 0) {
                        container.getChildren().get(i).getComponent().setLocation((int) container.getComponent().getLocation().getX(), (int) container.getComponent().getLocation().getY());
                        continue;
                    }

                    Component last = container.getChildren().get(i - 1);
                    container.getChildren().get(i).getComponent().setLocation((int) container.getComponent().getLocation().getX(), (int) (last.getComponent().getLocation().getY() + last.getComponent().getHeight()));
                }
                break;
            }
            case "FLEX": {
                if (!(component instanceof Container))
                    break;

                Container container = (Container) component;
                for (int i = 0; i < container.getChildren().size(); i++) {
                    if (i == 0) {
                        container.getChildren().get(i).getComponent().setLocation((int) container.getComponent().getLocation().getX(), (int) container.getComponent().getLocation().getY());
                        continue;
                    }

                    Component last = container.getChildren().get(i - 1);
                    container.getChildren().get(i).getComponent().setLocation((int) last.getComponent().getLocation().getX() + last.getComponent().getWidth(), (int) (component.getComponent().getLocation().getY()));
                }
                break;
            }
        }
    })),
    TEXT("text", ((component, graphics, attributeValue) -> component.text(attributeValue.toString())));

    private final String name;
    private final AttributeHandle attributeHandle;

    Attribute(String name, AttributeHandle attributeHandle) {
        this.name = name;
        this.attributeHandle = attributeHandle;
    }

    public String getName() {
        return this.name;
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