package me.xiaoying.window.event.component;

import me.xiaoying.window.component.Component;
import me.xiaoying.window.event.Event;

public abstract class ComponentEvent extends Event {
    private final Component component;

    public ComponentEvent(Component component) {
        this.component = component;
    }

    public Component getComponent() {
        return this.component;
    }
}