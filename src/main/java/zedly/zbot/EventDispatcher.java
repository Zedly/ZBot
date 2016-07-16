package zedly.zbot;

import zedly.zbot.api.event.EventHandler;
import zedly.zbot.api.event.Listener;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import zedly.zbot.api.event.Event;

public class EventDispatcher {

    private final Collection<Listener> listeners = new ArrayList<>();
    private final ArrayList<Listener> listenersToAdd = new ArrayList<>();
    private final ArrayList<Listener> listenersToRemove = new ArrayList<>();

    /**
     * Dispatch an event to the registered handlers.
     */
    public synchronized void dispatchEvent(Event event) {
        while (!listenersToRemove.isEmpty()) {
            listeners.remove(listenersToRemove.remove(0));
        }
        while (!listenersToAdd.isEmpty()) {
            listeners.add(listenersToAdd.remove(0));
        }
        for (Listener listener : listeners) {
            dispatchEventTo(event, listener);
        }
    }

    private synchronized void dispatchEventTo(Event event, Listener listener) {
        Collection<Method> methods = findMatchingEventHandlerMethods(listener, event.getClass());
        for (Method method : methods) {
            try {
                // Make sure the method is accessible (JDK bug ?)
                method.setAccessible(true);

                if (method.getParameterTypes().length == 0) {
                    method.invoke(listener);
                }
                if (method.getParameterTypes().length == 1) {
                    method.invoke(listener, event);
                }
                if (method.getParameterTypes().length == 2) {
                    method.invoke(listener, this, event);
                }
            } catch (Exception e) {
                System.err.println("Could not invoke event handler!");
                e.printStackTrace(System.err);
            }
        }
    }

    /**
     * Find all methods from the <em>handler</em> object that must be called,
     * based on the presence of the HandleEvent annotation.
     */
    private Collection<Method> findMatchingEventHandlerMethods(Listener listener, Class eventClass) {
        Method[] methods = listener.getClass().getDeclaredMethods();
        Collection<Method> result = new ArrayList<>();
        for (Method method : methods) {
            if (canHandleEvent(method, eventClass)) {
                result.add(method);
            }
        }
        return result;
    }

    /**
     * Look for the annotation values.
     */
    private boolean canHandleEvent(Method method, Class<? extends Object> eventClass) {
        EventHandler handleEventAnnotation = method.getAnnotation(EventHandler.class);
        if (handleEventAnnotation != null) {
            Parameter[] params = method.getParameters();
            if (params.length == 1 && params[0].getType() == eventClass) {
                return true;
            }
        }
        return false;
    }

    public synchronized void addHandler(Listener listener) {
        this.listenersToAdd.add(listener);
    }

    /**
     * Removes an event handler.
     */
    public synchronized void removeHandler(Listener listener) {
        this.listenersToRemove.add(listener);
    }
}
