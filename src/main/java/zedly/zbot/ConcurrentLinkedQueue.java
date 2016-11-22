package zedly.zbot;

public class ConcurrentLinkedQueue<E> {

    private class Node {

        Node next;
        Object object;

        private Node(Object object) {
            this.object = object;
        }
    }
    private Node first;
    private Node last;
    private long size = 0;

    public synchronized boolean enq(E object) {
        if (object == null) {
            return false;
        } else {
            Node node = new Node(object);
            if (size == 0) {
                first = node;
            } else {
                last.next = node;
            }
            last = node;
            size++;
            notify();
            return true;
        }
    }

    public synchronized E deq() throws InterruptedException {
        while (size == 0) {
            wait();
        }
        Object object = first.object;
        first = first.next;
        size--;
        return (E) object;
    }

    public synchronized E tryDeq() {
        if (size == 0) {
            return null;
        } else {
            Object object = first.object;
            first = first.next;
            size--;
            return (E) object;
        }
    }

    public synchronized void printQueue() {
        if (size != 0) {
            Node node = first;
            while (node != null) {
                System.out.println(node.object.toString());
                node = node.next;
            }
        }
    }

    public synchronized E peek() {
        if (size == 0) {
            return null;
        } else {
            return (E) first.object;
        }
    }

    public synchronized long size() {
        return size;
    }

    public synchronized void clear() {
        first = null;
        last = null;
        size = 0;
    }
}