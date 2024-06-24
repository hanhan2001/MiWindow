package me.xiaoying.window.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class EventManager {
    public void callEvent(Event event) {
        synchronized (this) {
            this.fireEvent(event);
        }
    }

    private void fireEvent(Event event) {
        HandlerList handlers = event.getHandlers();
        RegisteredListener[] listeners = handlers.getRegisteredListener();
        if (listeners == null)
            return;

        for (RegisteredListener registration : listeners) {
            try {
                registration.callEvent(event);
            } catch (AuthorNagException e) {
                System.err.println(e.getMessage());
            } catch (Throwable throwable) {
                System.err.println("Could not pass event " + event.getClass().getName());
                throwable.printStackTrace();
            }
        }
    }

    public void registerEvents(Listener listener) {
        for (Map.Entry<Class<? extends Event>, Set<RegisteredListener>> entry : this.createRegisteredListeners(listener).entrySet())
            this.getEventListeners(getRegistrationClass(entry.getKey())).registerAll(entry.getValue());
    }


    public void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor) {
        this.getEventListeners(event).register(new RegisteredListener(listener, executor, priority));
    }

    public Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(Listener listener) {
        Map<Class<? extends Event>, Set<RegisteredListener>> ret = new HashMap<>();

        HashSet<Method> methods;
        try {
            Method[] publicMethods = listener.getClass().getMethods();
            Method[] privateMethods = listener.getClass().getDeclaredMethods();
            methods = new HashSet<>(publicMethods.length + privateMethods.length, 1.0F);

            Collections.addAll(methods, publicMethods);
            Collections.addAll(methods, privateMethods);
        } catch (NoClassDefFoundError e) {
            System.err.println("failed to register events for {} because {} dose not exists.");
//            LACore.getLogger().warn("Plugin {} has failed to register events for {} because {} dose not exists.", plugin.getDescription().getName(), listener.getClass().getName(), e.getMessage());
            return ret;
        }

        Iterator<Method> iterator = methods.iterator();

        while (true) {
            while(true) {
                Method method;
                EventHandler eh;
                do {
                    do {
                        do {
                            if (!iterator.hasNext())
                                return ret;

                            method = iterator.next();
                            eh = method.getAnnotation(EventHandler.class);
                        } while (eh == null);
                    } while (method.isBridge());
                } while (method.isSynthetic());

                Class<?> checkClass;
                if (method.getParameterTypes().length != 1 || !Event.class.isAssignableFrom(checkClass = method.getParameterTypes()[0])) {
                    System.err.println(" attempted to register an invalid EventHandler method signature " + method.toGenericString() + " " + listener.getClass().getName());
//                    LACore.getLogger().warn("{} attempted to register an invalid EventHandler method signature \"{}\" in {}", plugin.getDescription().getName(), method.toGenericString(), listener.getClass().getName());
                    return null;
                }

                final Class<? extends Event> eventClass = checkClass.asSubclass(Event.class);
                method.setAccessible(true);
                Set<RegisteredListener> eventSet = ret.get(eventClass);
                if (eventSet == null) {
                    eventSet = new HashSet<>();
                    ret.put(eventClass, eventSet);
                }
                Method finalMethod = method;
                EventExecutor executor = (listener1, event) -> {
                    if (event.isCancelled())
                        return;
                    try {
                        finalMethod.invoke(listener1, event);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                };
                eventSet.add(new RegisteredListener(listener, executor, EventPriority.LOWEST));
            }
        }
    }

    private HandlerList getEventListeners(Class<? extends Event> type) {
        try {
            Method method = this.getRegistrationClass(type).getDeclaredMethod("getHandlerList");
            method.setAccessible(true);
            if (!Modifier.isStatic(method.getModifiers()))
                throw new IllegalAccessException("getHandlerList must be static");

            return (HandlerList) method.invoke(null);
        } catch (Exception var3) {
            throw new IllegalEventAccessException("Error while registering listener for event type " + type.toString() + ": " + var3);
        }
    }

    private Class<? extends Event> getRegistrationClass(Class<? extends Event> clazz) {
        try {
            clazz.getDeclaredMethod("getHandlerList");
            return clazz;
        } catch (NoSuchMethodException noSuchMethodException) {
            if (clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Event.class) && Event.class.isAssignableFrom(clazz.getSuperclass()))
                return getRegistrationClass(clazz.getSuperclass().asSubclass(Event.class));
            throw new IllegalEventAccessException("Unable to find handler list for event " + clazz.getName() + ". Static getHandlerList method required!");
        }
    }
}