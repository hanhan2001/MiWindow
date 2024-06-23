package me.xiaoying.window.event;

public class RegisteredListener {
    private final Listener listener;
    private final EventExecutor executor;
    private final EventPriority priority;

    public RegisteredListener(Listener listener, EventExecutor executor, EventPriority priority) {
        this.listener = listener;
        this.executor = executor;
        this.priority = priority;
    }

    public void callEvent(Event event) throws EventException {
        if (event == null || event.isCancelled())
            return;

        this.executor.execute(this.listener, event);
    }

    public Listener getListener() {
        return listener;
    }

    public EventPriority getPriority() {
        return priority;
    }
}