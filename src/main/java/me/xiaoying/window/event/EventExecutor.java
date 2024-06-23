package me.xiaoying.window.event;

/**
 * Event executor
 */
public interface EventExecutor {
    void execute(Listener listener, Event event) throws EventException;
}