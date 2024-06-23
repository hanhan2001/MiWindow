package me.xiaoying.window.event.window;

import me.xiaoying.window.Window;
import me.xiaoying.window.event.Event;

public abstract class WindowEvent extends Event {
    private final Window window;

    public WindowEvent(Window window) {
        this.window = window;
    }

    public Window getWindow() {
        return this.window;
    }
}