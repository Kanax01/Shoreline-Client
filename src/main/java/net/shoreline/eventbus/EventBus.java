package net.shoreline.eventbus;

import net.shoreline.eventbus.annotation.EventListener;
import net.shoreline.eventbus.event.Event;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class EventBus {
    public static final EventBus INSTANCE = new EventBus();

    private final Map<Class<? extends Event>, InvokerNode> event2InvokerMap = new ConcurrentHashMap<>();

    public void dispatch(Event event) {
        InvokerNode head = event2InvokerMap.get(event.getClass());
        if (head == null) {
            return;
        }
        InvokerNode current = head.next;
        while (current != null) {
            if (!event.isCanceled() || current.receiveCanceled) {
                current.invoker.invoke(event);
            }
            current = current.next;
        }
    }

    public void subscribe(Object subscriber) {
        Class<?> clazz = subscriber.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            EventListener listener = method.getAnnotation(EventListener.class);
            if (listener == null) {
                continue;
            }
            Class<?>[] paramTypes = method.getParameterTypes();
            if (paramTypes.length != 1 || !Event.class.isAssignableFrom(paramTypes[0])) {
                continue;
            }
            method.setAccessible(true);
            @SuppressWarnings("unchecked")
            Class<? extends Event> eventType = (Class<? extends Event>) paramTypes[0];
            Invoker invoker = event -> {
                try {
                    method.invoke(subscriber, event);
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException("Failed invoking listener: " + method, e);
                }
            };
            InvokerNode node = new InvokerNode(invoker, subscriber, listener.priority(), listener.receiveCanceled());
            event2InvokerMap.compute(eventType, (key, head) -> {
                if (head == null) {
                    head = InvokerNode.sentinel();
                }
                InvokerNode prev = head;
                InvokerNode curr = head.next;
                while (curr != null && curr.priority >= node.priority) {
                    prev = curr;
                    curr = curr.next;
                }
                prev.next = node;
                node.next = curr;
                return head;
            });
        }
    }

    public void unsubscribe(Object subscriber) {
        for (InvokerNode head : event2InvokerMap.values()) {
            InvokerNode prev = head;
            InvokerNode curr = head.next;
            while (curr != null) {
                if (curr.subscriber == subscriber) {
                    prev.next = curr.next;
                } else {
                    prev = curr;
                }
                curr = curr.next;
            }
        }
    }

    public static final class InvokerNode {
        private InvokerNode next;
        private final Invoker invoker;
        private final Object subscriber;
        private final int priority;
        private final boolean receiveCanceled;

        private InvokerNode(Invoker invoker, Object subscriber, int priority, boolean receiveCanceled) {
            this.invoker = invoker;
            this.subscriber = subscriber;
            this.priority = priority;
            this.receiveCanceled = receiveCanceled;
        }

        private static InvokerNode sentinel() {
            return new InvokerNode(event -> {}, null, Integer.MIN_VALUE, true);
        }
    }

    @FunctionalInterface
    public interface Invoker {
        void invoke(Event event);
    }
}
