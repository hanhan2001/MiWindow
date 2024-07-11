package me.xiaoying.window.component;

import me.xiaoying.window.awt.MiButton;

import java.awt.*;

public class Div extends Container {
    private final MiButton component = new MiButton(this);

    public Div(String name) {
        this.setComponent(this.component);

        this.name(name);

        this.init();
    }

    public Div(int width, int height) {
        this.setComponent(this.component);

        this.width(width + "px");
        this.height(height + "px");

        this.init();
    }

    public Div(String name, int width, int height) {
        this.setComponent(this.component);

        this.name(name);
        this.width(width + "px");
        this.height(height + "px");

        this.init();
    }

    public void init() {
        this.component.setBorder(null);
        this.component.setFocusPainted(false);
        this.component.setBorderPainted(false);
        this.component.setMargin(new Insets(0, 0, 0, 0));

        this.component.repaint();
        this.component.setVisible(true);
    }
}