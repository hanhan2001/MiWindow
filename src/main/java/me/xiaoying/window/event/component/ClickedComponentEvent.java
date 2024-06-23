package me.xiaoying.window.event.component;

import me.xiaoying.window.component.Component;
import me.xiaoying.window.event.HandlerList;

/**
 * Event component clicked
 */
public class ClickedComponentEvent extends ComponentEvent {
    private final static HandlerList handlers = new HandlerList();

    public ClickedComponentEvent(Component component) {
        super(component);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}