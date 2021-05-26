package xyz.fe1.datastruct;

import java.security.SecureRandom;
import java.util.Arrays;

public class SkipList<K extends Comparable<K>, V> {

    /**
     * 默认的跳表最大高度
     */
    private static final int DEFAULT_MAXIMUM_LEVEL = 1 << 10;
    private static final SecureRandom RAND = new SecureRandom();
    private final int maximumLevel;
    private int level;
    private final Node<K, V> head;
    private int size;

    public SkipList() {
        this(DEFAULT_MAXIMUM_LEVEL);
    }

    public SkipList(int maximumLevel) {
        this.maximumLevel = maximumLevel;
        head = new Node<>(null, null);
        this.level = head.level;
        this.size = 0;
    }

    /**
     * 获取操作
     * @param key
     * @return
     */
    public V get(K key) {
        var cursor = head;
        int l;
        for (l = this.level - 1; l >= 0; --l) {
            if (cursor.forwards[l] != null) {
                var compared = cursor.forwards[l].key.compareTo(key);
                if (compared == 0) return cursor.forwards[l].val;
                else if (compared < 0) cursor = cursor.forwards[l++];
            }
        }
        return null;
    }

    /**
     * 删除操作，遍历删除每一层的结点索引，当每一层的索引都被删除之后，即可视为结点已删除
     * @param key
     * @return
     */
    public V del(K key) {
        var cursor = head;
        int l;
        V val = null;
        for (l = this.level - 1; l >= 0; --l) {
            if (cursor.forwards[l] != null) {
                var compared = cursor.forwards[l].key.compareTo(key);
                if (compared == 0) {
                    if (val == null) {
                        val = cursor.forwards[l].val;
                        size--;
                    }
                    /**
                     * 删除当前层的结点索引
                     */
                    cursor.forwards[l] = cursor.forwards[l].forwards[l];
                }
                else if (compared < 0) cursor = cursor.forwards[l++];
            }
        }
        return val;
    }

    /**
     * 插入操作，当随机结点高度大于跳表的高度时，需要对头指针进行扩容操作，使头指针始终处于跳表最高点
     * @param key key
     * @param val val
     */
    public void put(K key, V val) {
        var node = new Node<>(key, val, nextLevel());
        /**
         * 扩容头指针
         */
        if (node.level > this.level) resizeHead(node.level);
        /**
         * 遍历每次向下走的时候记录当前结点，以便于最后对新结点进行插入操作
         */
        var visitForwards = new Node[this.level];
        var cursor = head;
        int l;
        for (l = this.level - 1; l >= 0; --l) {
            if (cursor.forwards[l] == null) visitForwards[l] = cursor;  // down <-> --l
            else {
                var compared = cursor.forwards[l].key.compareTo(key);
                if (compared == 0) {
                    cursor.forwards[l].val = node.val;
                    return;
                }
                else if (compared > 0) visitForwards[l] = cursor;  // down <-> --l
                else cursor = cursor.forwards[l++];
            }
        }
        /**
         * 更新每层结点得尾指针指向新结点
         */
        for (l = 0; l < node.level; ++l) {
            var next = visitForwards[l].forwards[l];
            visitForwards[l].forwards[l] = node;
            node.forwards[l] = next;
        }
        size++;
    }

    /**
     * 头指针扩容操作
     * @param level 跳表层数
     */
    private void resizeHead(int level) {
        this.head.forwards = Arrays.copyOf(head.forwards, level);
        this.level = head.level = level;
    }

    /**
     * 随机获取层数，1(50%)  2(25%)  3(12.5%)  4(6.25%) ....
     * @return
     */
    private int nextLevel() {
        int l = 1;
        while (RAND.nextInt(2) == 0 && l < maximumLevel) l++;
        return l;
    }

    /**
     * 链表结点
     * @param <K> key
     * @param <V> val
     */
    private static class Node<K extends Comparable<K>, V> {
        /**
         * 当前结点的层数
         */
        int level;
        K key;
        V val;
        /**
         * 每一层所对应的next指针数组
         */
        Node<K, V>[] forwards;
        Node(K key, V val, int level) {
            this.key = key;
            this.val = val;
            this.level = level;
            this.forwards = new Node[level];
        }
        Node(K key, V val) {
            this(key, val, 1);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "level=" + level +
                    ", key=" + key +
                    ", val=" + val +
                    '}';
        }
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        builder.append('{').append(" maxLevel: ").append(this.level).append('\n');
        for (int l = this.level - 1;l >= 0; --l) {
            builder.append("\t[ ");
            var cursor = head.forwards[l];
            while (cursor != null) {
                builder.append(cursor).append(" ");
                cursor = cursor.forwards[l];
            }
            builder.append(" ]").append('\n');
        }
        builder.append('}');
        return builder.toString();
    }

    public static void main(String[] args) {
        var skipList = new SkipList<Integer, Integer>();
        for (int i = 1; i <= 100; ++i) {
            skipList.put(i, i);
        }
        skipList.del(50);
        System.out.println(skipList);

    }
}
