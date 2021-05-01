package xyz.fe1.datastruct;

import java.util.NoSuchElementException;

public class Stack<T> {

    private int size;
    private Node<T> top;

    public T pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack is empty!");
        var v = top.elem;
        if (top.prev != null) {
            top = top.prev;
            top.next = null;
        }
        size--;
        return v;
    }

    public void push(T t) {
        var node = new Node<>(t);
        if (top != null) {
            top.next = node;
            node.prev = top;
        }
        size++;
        top = node;
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
        var stack = new Stack<Integer>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.printf("dequeue: size(%d) [%d -> %d -> %d] size(%d)\n", stack.size(),
                stack.pop(), stack.pop(), stack.pop(), stack.size());
    }
}
