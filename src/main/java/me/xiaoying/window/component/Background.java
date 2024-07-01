package me.xiaoying.window.component;

import java.awt.*;

public class Background implements Cloneable {
    private final Component component;
    private Color color = Color.WHITE;

    public Background(Component component) {
        this.component = component;
    }

    public Background color(Color color) {
        this.color = color;
        this.component.getAttributes().set(Attribute.BACKGROUND_COLOR, color);
        return this;
    }

    public Background color(me.xiaoying.window.Color color) {
        this.color = color.toAWTColor();
        this.component.getAttributes().set(Attribute.BACKGROUND_COLOR, color.toAWTColor());
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