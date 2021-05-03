package xyz.fe1.datastruct;

import java.util.NoSuchElementException;

/**
 * 使用数组存储完全二叉堆
 * @param <T>
 */
public class Heap<T extends Comparable<T>> {

    private Object[] heap;
    private int size;
    private static final int INITIAL_SIZE = 1 << 4;
    private static final int TOP = 1;

    public Heap() {
        heap = new Object[INITIAL_SIZE];
    }

    /**
     * 插入操作:
     *      将元素插入数组末尾，再上浮
     * @param t
     */
    public void insert(T t) {
        heap[++size] = t;
        swim(size);
        if (size == heap.length - 2) {
            grow();
        }
    }

    /**
     * 删除操作
     *      删除堆顶元素，再将数组末尾元素移至堆顶进行下沉操作
     * @return
     */
    public T delMax() {
        if (isEmpty()) throw new NoSuchElementException("Heap is Empty!");
        Object t = heap[TOP];
        heap[TOP] = heap[size--];
        heap[size + 1] = null;
        sink(TOP);
        return (T) t;
    }

    /**
     * 上浮
     * @param target 上浮元素的起始下标
     */
    private void swim(int target) {
        while (target > 1 && cmp(target, parent(target)) > 0) {
            swap(target, parent(target));
            target = parent(target);
        }
    }

    private int cmp(int i, int j) {
        return ((T) heap[i]).compareTo((T) heap[j]);
    }

    private void sink(int target) {
        for (;;) {
            var swap = leftChild(target);
            if (swap >= size) break;
            if (cmp(swap, swap + 1) < 0) swap++;
            if (cmp(target, swap) > 0) break;
            swap(swap, target);
            target = swap;
        }
    }

    private void swap(int i, int j) {
        var tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    public T max() {
        return (T) heap[TOP];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void grow() {
        var newHeap = new Object[heap.length * 2];
        copyArray(heap, newHeap);
        heap = newHeap;
    }

    private static void copyArray(Object[] source, Object[] target) {
        copyArray(source, target, 0, source.length);
    }

    private static void copyArray(Object[] source, Object[] target, int begin, int end) {
        if (begin < 0 || end < 0 || begin > end || end >= source.length || end >= target.length)
            throw new IllegalArgumentException();
        while (begin <= end) target[begin] = source[begin++];
    }

    private int parent(int child) {
        return child / 2;
    }

    private int leftChild(int parent) {
        return parent * 2;
    }

    private int rightChild(int parent) {
        return parent * 2 + 1;
    }

    public static void main(String[] args) {
        var heap = new Heap<Integer>();
        heap.insert(1);
        System.out.println("TOP: " + heap.max());
        heap.insert(2);
        System.out.println("TOP: " + heap.max());
        heap.insert(3);
        System.out.println("TOP: " + heap.max());
        heap.insert(4);
        System.out.println("TOP: " + heap.max());
        heap.insert(5);
        System.out.println("TOP: " + heap.max());
        heap.delMax();
        System.out.println("TOP: " + heap.max());
        heap.delMax();
        System.out.println("TOP: " + heap.max());
        heap.delMax();
        System.out.println("TOP: " + heap.max());
        heap.delMax();
        System.out.println("TOP: " + heap.max());
    }
}
