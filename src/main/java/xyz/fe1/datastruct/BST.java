package xyz.fe1.datastruct;

/**
 * 二叉搜索树
 *      K V结构，不支持重复元素
 *      删除操作最麻烦
 */
public class BST<K extends Comparable<K>, V> {

    private Entry<K, V> root;
    private int size;

    public BST() {

    }

    /**
     * 若未找到则返回空
     * @param key 键
     * @return 值
     */
    public V get(K key) {
        var cursor = root;
        for (;;) {
            if (cursor == null) return null;
            var compared = key.compareTo(cursor.key);
            if (compared == 0) return cursor.val;
            else if (compared > 0) cursor = cursor.right;
            else cursor = cursor.left;
        }
    }

    /**
     * 删除操作，最麻烦
     * @param key 要删除的键
     */
    public void delete(K key) {
        if (root == null) return;
        class Closure {

            Entry<K, V> delete(Entry<K, V> node) {
                if (node == null) return null;
                var compared = key.compareTo(node.key);
                if (compared > 0) node.right = delete(node.right);
                else if (compared < 0) node.left = delete(node.left);
                else {
                    size--;
                    if (node.left == null) return node.right;  // 左子树为空的删除操作
                    else if (node.right == null) return node.left; // 右子树为空的删除操作
                    else {
                        /*
                         * 处理同时存在左子树和右子树的情况 (核心：用要删除节点的最小右子树替换要删除的节点，至于为什么 :)
                         *      步骤：     1、暂存要删除节点
                         *                2、将当前节点指向要删除节点的最小右子树
                         *                3、删除最小右子树
                         *                4、将最小右子树的左子树指向暂存节点的左子树完成替换操作
                         */
                        var tmp = node;
                        node = min(tmp.right);
                        node.right = deleteMin(tmp.right);
                        node.left = tmp.left;
                    }
                }
                return node;
            }

            private Entry<K, V> min(Entry<K, V> node) {
                if (node.left == null) return node;
                return min(node.left);
            }

            private Entry<K ,V> deleteMin(Entry<K, V> node) {
                if (node.left == null) return node.right;
                node.left = deleteMin(node.left);
                return node;
            }
        }
        root = new Closure().delete(root);
    }

    /**
     * 中序遍历(有序访问)
     * @param visitor 回调
     */
    public void forEach(Visitor<K, V> visitor) {
        if (root == null) return;
        class Closure {
            void visit(Entry<K, V> node) {
                if (node == null) return;
                visit(node.left);
                visitor.visit(node);
                visit(node.right);
            }
        }
        new Closure().visit(root);
    }

    public interface Visitor<K, V> {
        void visit(Entry<K, V> entry);
    }

    /**
     * 插入节点，若key重复则替换
     * @param key 键
     * @param val 值
     */
    public void put(K key, V val) {
        var entry = new Entry<>(key, val);
        if (root == null) {
            root = entry;
            size++;
        } else {
            var cursor = root;
            for (;;) {
                var compared = key.compareTo(cursor.key);
                if (compared == 0) {
                    cursor.val = val;
                    break;
                } else if (compared > 0) {
                    if (cursor.right == null) {
                        cursor.right = entry;
                        size++;
                        break;
                    } else {
                        cursor = cursor.right;
                    }
                } else {
                    if (cursor.left == null) {
                        cursor.left = entry;
                        size++;
                        break;
                    } else {
                        cursor = cursor.left;
                    }
                }
            }
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }


    public static class Entry<K, V> {

        Entry<K, V> left, right;
        private final K key;
        private V val;

        Entry(K key, V val) {
            this.key = key;
            this.val = val;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return val;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "key=" + key +
                    ", val=" + val +
                    '}';
        }
    }

    public static void main(String[] args) {
        var bst = new BST<Integer, Integer>();
        bst.put(4, 1);
        bst.put(5, 1);
        bst.put(6, 1);
        bst.put(1, 1);
        bst.put(2, 1);
        bst.put(3, 1);
    }

}

