package me.xiaoying.window.event.window;

import me.xiaoying.window.Window;
import me.xiaoying.window.event.HandlerList;

public class WindowCloseEvent extends WindowEvent {
    public static HandlerList handlers = new HandlerList();

    public WindowCloseEvent(Window window) {
        super(window);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}