package me.xiaoying.window.component;

import java.awt.*;

public class Background {
    private Color color;

    public Background color(Color color) {
        this.color = color;
        return this;
    }

    public Background color(me.xiaoying.window.Color color) {
        this.color = color.toAWTColor();
        return this;
    }

    public Color color() {
        return this.color;
    }
}