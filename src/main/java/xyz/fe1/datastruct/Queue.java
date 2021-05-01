package xyz.fe1.datastruct;

import java.util.NoSuchElementException;

public class Queue<T> {

    private int size;
    private Node<T> head;
    private Node<T> tail;

    public void enqueue(T t) {
        var node = new Node<>(t);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty!");
        var e = head.elem;
        if (head.next != null) head = head.next;
        else head = tail = null;
        size--;
        return e;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private static class Node<E> {
        Node<E> prev;
        E elem;
        Node<E> next;

        Node(E elem) {
            this.elem = elem;
        }

        E getElem() {
            return elem;
        }
    }

    public static void main(String[] args) {
        var queue = new Queue<Integer>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        System.out.printf("dequeue: size(%d) [%d -> %d -> %d] size(%d)\n", queue.size(),
                queue.dequeue(), queue.dequeue(), queue.dequeue(), queue.size());
    }
}
