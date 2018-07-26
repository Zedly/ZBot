package zedly.zbot;

import zedly.zbot.event.EventHandler;
import zedly.zbot.event.Listener;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Predicate;
import zedly.zbot.event.Event;

public class EventDispatcher {

    private final LinkedHashMap<Listener, HashMap<Class, Collection<Method>>> permanentEventMethodCache = new LinkedHashMap<>();
    //Kind of pointless because we are dealing with ~10 entries at all times
    private final HashMap<Class, List<Consumer>> temporaryEventMethodCache = new HashMap<>();
    private final Collection<Listener> listeners = new ArrayList<>();
    private final LinkedList<Listener> listenersToRemove = new LinkedList<>();

    /**
     * Totally faster than the previous method. Not.
     */
    public synchronized void dispatchEvent(Event event) {
        // Loop through all registered Listener

        for (Entry<Listener, HashMap<Class, Collection<Method>>> eventMethodMap : permanentEventMethodCache.entrySet()) {
            // Loop through all methods accepting this class
            HashMap<Class, Collection<Method>> listenerMethods = eventMethodMap.getValue();

            for (Class clazz : listenerMethods.keySet()) {
                if (clazz.isAssignableFrom(event.getClass())) {
                    for (Method method : listenerMethods.get(clazz)) {
                        try {
                            method.invoke(eventMethodMap.getKey(), event);
                        } catch (Exception e) {
                            System.err.println("Could not invoke event handler!");
                            e.printStackTrace(System.err);
                        }
                    }
                }
            }
        }
        for (Listener l : listenersToRemove) {
            permanentEventMethodCache.remove(l);
        }
        listenersToRemove.clear();
    }

    private boolean canHandleEvents(Method method) {
        EventHandler handleEventAnnotation = method.getAnnotation(EventHandler.class);
        if (handleEventAnnotation != null) {
            Parameter[] params = method.getParameters();
            if (params.length == 1 && Event.class.isAssignableFrom(params[0].getType())) {
                return true;
            }
        }
        return false;
    }

    public synchronized void addPermanentHandler(Listener listener) {
        HashMap<Class, Collection<Method>> listenerMethods = generateMethodMap(listener);
        permanentEventMethodCache.put(listener, listenerMethods);
    }

    /**
     * Removes an event handler.
     */
    public synchronized void removePermanentHandler(Listener listener) {
        listenersToRemove.add(listener);
    }

    private HashMap<Class, Collection<Method>> generateMethodMap(Listener listener) {
        HashMap<Class, Collection<Method>> listenerMethods = new HashMap<>();
        Method[] methods = listener.getClass().getDeclaredMethods();

        Collection<Method> result;
        for (Method method : methods) {
            if (canHandleEvents(method)) {
                Class eventClass = method.getParameters()[0].getType();
                // If this event class is already present, append methods to its map entry,
                // Otherwise add a new key
                if (listenerMethods.containsKey(eventClass)) {
                    result = listenerMethods.get(eventClass);
                } else {
                    result = new ArrayList<>();
                    listenerMethods.put(eventClass, result);
                }
                method.setAccessible(true);
                result.add(method);
            }
        }
        return listenerMethods;
    }

}
