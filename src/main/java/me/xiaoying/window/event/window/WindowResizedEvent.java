package me.xiaoying.window.event.window;

import me.xiaoying.window.Window;
import me.xiaoying.window.event.HandlerList;

public class WindowResizedEvent extends WindowEvent {
    private static final HandlerList handlers = new HandlerList();
    private final int width;
    private final int height;

    public WindowResizedEvent(Window window, int width, int height) {
        super(window);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}