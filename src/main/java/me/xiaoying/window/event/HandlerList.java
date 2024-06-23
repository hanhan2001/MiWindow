package me.xiaoying.window.event;

import java.util.*;

/**
 * Event HandlerList
 */
public class HandlerList {
    private volatile RegisteredListener[] handlers = null;
    private final EnumMap<EventPriority, List<RegisteredListener>> handlerslots;
    private static final ArrayList<HandlerList> allLists = new ArrayList<>();

    public HandlerList() {
        this.handlerslots = new EnumMap<>(EventPriority.class);
        for (EventPriority value : EventPriority.values())
            this.handlerslots.put(value, new ArrayList<>());

        synchronized (allLists) {
            allLists.add(this);
        }
    }

    public synchronized void bake() {
        if (this.handlers != null)
            return;

        List<RegisteredListener> registeredListeners = new ArrayList<>();
        for (Map.Entry<EventPriority, List<RegisteredListener>> eventPriorityListEntry : this.handlerslots.entrySet())
            registeredListeners.addAll(eventPriorityListEntry.getValue());

        this.handlers = registeredListeners.toArray(new RegisteredListener[0]);
    }

    public static void bakeAll() {
        synchronized (allLists) {
            for (HandlerList allList : allLists)
                allList.bake();
        }
    }

    public synchronized void register(RegisteredListener listener) {
        if (this.handlerslots.get(listener.getPriority()).contains(listener))
            throw new IllegalStateException("This listener is already registered to priority " + listener.getPriority().toString());

        this.handlers = null;
        this.handlerslots.get(listener.getPriority()).add(listener);
    }

    public void registerAll(Collection<RegisteredListener> listeners) {
        for (RegisteredListener listener : listeners)
            this.register(listener);
    }

    public synchronized void unregister(RegisteredListener listener) {
        if(!this.handlerslots.get(listener.getPriority()).remove(listener))
            return;
        this.handlers = null;
    }

    public synchronized void unregister(Listener listener) {
        boolean changed = false;
        for (List<RegisteredListener> value : this.handlerslots.values()) {
            for (ListIterator<RegisteredListener> i = value.listIterator(); i.hasNext(); ) {
                if (!i.next().getListener().equals(listener))
                    continue;

                i.remove();
                changed = true;
            }
        }

        if (changed)
            this.handlers = null;
    }

    public static void unregisterAll() {
        synchronized (allLists) {
            for (HandlerList h : allLists) {
                synchronized (h) {
                    for (List<RegisteredListener> list : h.handlerslots.values())
                        list.clear();

                    h.handlers = null;
                }
            }
        }
    }

    public static void unregisterAll(Listener listener) {
        synchronized (allLists) {
            for (HandlerList handlerList : allLists)
                handlerList.unregister(listener);
        }
    }

    public RegisteredListener[] getRegisteredListener() {
        RegisteredListener[] handlers;
        while ((handlers = this.handlers) == null)
            this.bake();

        return handlers;
    }

    public static List<HandlerList> getHandlerLists() {
        synchronized (allLists) {
            return (List<HandlerList>) allLists.clone();
        }
    }
}