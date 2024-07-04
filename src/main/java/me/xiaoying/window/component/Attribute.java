package me.xiaoying.window.component;

import me.xiaoying.window.Color;

import java.util.HashMap;
import java.util.Map;

public enum Attribute implements Cloneable {
    NAME("name"),
    WIDTH("width"),
    HEIGHT("height"),
    COLOR("color"),
    BACKGROUND_COLOR("background_color"),
    FONT_SIZE("font_size"),
    FONT_FAMILY("font_family");

    private final String name;

    Attribute(String name) {
        this.name = name;
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
        return map;
    }


    @Override
    public String toString() {
        return this.getName();
    }
}