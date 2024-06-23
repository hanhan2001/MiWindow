package me.xiaoying.window.event;

public abstract class Event extends Cancellable {
    public abstract HandlerList getHandlers();
}