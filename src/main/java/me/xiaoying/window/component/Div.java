package me.xiaoying.window.component;

import me.xiaoying.window.awt.MiPanel;

public class Div extends Container {
    private final MiPanel miPanel = new MiPanel(this);

    public Div(String name) {
        this.setComponent(this.miPanel);

        this.name(name);

        this.init();
    }

    public Div(int width, int height) {
        this.setComponent(this.miPanel);

        this.width(width + "px");
        this.height(height + "px");

        this.init();
    }

    public Div(String name, int width, int height) {
        this.setComponent(this.miPanel);

        this.name(name);
        this.width(width + "px");
        this.height(height + "px");

        this.init();
    }

    public void init() {

    }
}