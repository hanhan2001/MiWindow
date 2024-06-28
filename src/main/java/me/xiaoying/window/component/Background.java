package me.xiaoying.window.component;

import java.awt.*;

public class Background implements Cloneable {
    private final Component component;
    private Color color;

    public Background(Component component) {
        this.component = component;
    }

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

    @Override
    protected Background clone() {
        try {
            Background background = (Background) super.clone();
            background.color = this.color;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}