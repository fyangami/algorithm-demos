package xyz.fe1.datastruct;

import java.util.NoSuchElementException;

/**
 * 红黑树
 *      - 自平衡的二叉搜索树
 *      - 结合了二叉搜索树和2-3树(平衡搜索树)的优缺点
 */
public class RedBlackTree<K extends Comparable<K>, V> {

    private Node<K, V> root;

    public RedBlackTree() {
    }

    /**
     * 插入、修改操作（非递归）
     */
    public void put0(K key, V val) {
        if (root == null) {
            root = new Node<>(key, val, false);
        } else {
            var cursor = root;
            for (;;) {
                var node = new Node<>(key, val, true);
                var compared = key.compareTo(cursor.key);
                if (compared == 0) cursor.val = val;
                else if (compared > 0) {
                    if (cursor.right == null) {
                        /*
                         * 插入位置
                         */
                        cursor.right = node;
                        node.parent = cursor;
                        break;
                    } else {
                        cursor = cursor.right;
                    }
                } else {
                    if (cursor.left == null) {
                        /*
                         * 插入位置
                         */
                        cursor.left = node;
                        node.parent = node;
                        break;
                    } else {
                        cursor = cursor.left;
                    }
                }
            }
            /*
             * 自平衡核心逻辑
             *      - 通过parent链遍历更新
             *      - 需注意引用问题，其余与递归方式一致
             */
            while (cursor != null) {
                Node<K, V> pp;
                boolean changeRoot = cursor.parent == null; // 确定是否需要替换root节点
                pp = cursor.parent == null ? root : cursor.parent;  // 确定父节点，用来改变指向
                var insertLeft = pp.left == cursor;  // 确定新节点插入在左边还是右边
                if (isRad(cursor.right) && !isRad(cursor.left)) {
                    if (changeRoot) {
                        root = rotateLeft(cursor);
                        root.parent = null;
                    }
                    else if (insertLeft) pp.left = rotateLeft(cursor);
                    else pp.right = rotateLeft(cursor);
                }
                if (isRad(cursor.left) && isRad(cursor.left.left)) {
                    if (pp == root) {
                        root = rotateLeft(cursor);
                        root.parent = null;
                    }
                    else if (insertLeft) pp.left = rotateRight(cursor);
                    else pp.right = rotateRight(cursor);
                }
                if (isRad(cursor.left) && isRad(cursor.right)) flip(cursor);
                if (cursor.left != null) cursor.left.parent = cursor;
                if (cursor.right != null) cursor.right.parent = cursor;
                cursor = cursor.parent;
            }
            root.red = false;
        }
    }

    /**
     * 插入、修改操作（递归）
     * @param key k
     * @param val v
     */
    public void put(K key, V val) {
        class Closure {
            Node<K, V> put(Node<K, V> node) {
                if (node == null) return new Node<>(key, val, true);
                var compared = key.compareTo(node.key);
                if (compared == 0) node.val = val;
                else if (compared > 0) node.right = put(node.right);
                else node.left = put(node.left);
                /*
                 * 自平衡的核心逻辑
                 *      - 如果当前节点只存在右链接 则进行左旋操作
                 *      - 如果当前节点同时存在两条左链接，则进行右旋操作 (此时右旋会将两条连续的左链接转为左右链接)
                 *      - 如果当前节点的同时存在左右链接，则颜色转换
                 */
                if (isRad(node.right) && !isRad(node.left)) node = rotateLeft(node);
                if (isRad(node.left) && isRad(node.left.left)) node = rotateRight(node);
                if (isRad(node.left) && isRad(node.right)) flip(node);

                if (node.left != null) node.left.parent = node;
                if (node.right != null) node.right.parent = node;
                return node;
            }
        }
        root = new Closure().put(root);
        /*
         * 根节点始终保持黑色
         */
        root.red = false;

        root.parent = null;
    }

    /**
     * 删除操作 (有问题)
     * @param key k
     */
    public void remove(K key) {
        if (root == null) throw new NoSuchElementException("tree is empty.");
        class Closure {

            Node<K, V> remove(Node<K, V> node) {
                var compared = key.compareTo(node.key);
                if (compared < 0) {
                    if (!isRad(node.left) && isRad(node.left.left)) node = moveRedLeft(node);
                    node.left = remove(node.left);
                } else {
                    if (isRad(node.left)) node = rotateRight(node);
                    if (compared == 0 && node.right == null) return null;
                    if (!isRad(node.right) && !isRad(node.right.left)) node = moveRedRight(node);
                    if (compared == 0) {
                        node.val = get(node.right, min(node.right).key).val;
                        node.key = min(node.right).key;
                        node.right = deleteMin(node.right);
                    } else node.right = remove(node.right);
                }
                return balance(node);
            }

            Node<K, V> deleteMin(Node<K, V> node) {
                if (node.left == null) return null;
                if (!isRad(node.left) && !isRad(node.left.left)) node = moveRedLeft(node);
                node.left = deleteMin(node.left);
                return balance(node);
            }

            Node<K, V> moveRedLeft(Node<K, V> node) {
                flip0(node);
                if (isRad(node.right.left)) {
                    node.right = rotateRight(node.right);
                    node = rotateLeft(node);
                }
                return node;
            }

            Node<K, V> moveRedRight(Node<K, V> node) {
                flip0(node);
                if (!isRad(node.left.left)) node = rotateRight(node);
                return node;
            }

            void flip0(Node<K, V> node) {
                node.red = false;
                node.left.red = true;
                node.right.red = true;
            }

            Node<K, V> balance(Node<K, V> node) {
                if (isRad(node.right) && !isRad(node.left)) node = rotateLeft(node);
                if (isRad(node.left) && isRad(node.left.left)) node = rotateRight(node);
                if (isRad(node.left) && isRad(node.right)) flip(node);
                return node;
            }
        }
        if (!isRad(root.left) && !isRad(root.right)) root.red = true;
        new Closure().remove(root);
        if (root != null) root.red = false;
    }


    private Node<K, V> min(Node<K, V> node) {
        if (node.left == null) return node;
        else return min(node.left);
    }

    private Node<K, V> get(Node<K, V> node, K key) {
        if (node == null) return null;
        var compared = key.compareTo(node.key);
        if (compared == 0) return node;
        else if (compared > 0) return get(node.right, key);
        else return get(node.left, key);
    }

    private boolean isRad(Node<K ,V> node) {
        return node != null && node.red;
    }

    /**
     * 颜色转换
     *      将两颗子树转为黑色，同时父节点转为红色
     * @param node 要转换的节点
     */
    private void flip(Node<K, V> node) {
        node.left.red = false;
        node.right.red = false;
        node.red = true;
    }

    /**
     * 左旋操作
     *
     * 旋转前：
     *            |
     *          node
     *              \
     *               rtd
     * 旋转后：
     *           |
     *          rtd
     *         /
     *       node
     * @param node 要旋转的节点
     * @return 旋转后的节点
     */
    private Node<K, V> rotateLeft(Node<K, V> node) {
        var rotated = node.right;

        node.right = rotated.left;
        rotated.left = node;

        rotated.red = node.red;
        node.red = true;
        return rotated;
    }

    public void forEach(Visitor<K, V> visitor) {
        class Closure {
            void visit(Node<K, V> node) {
                if (node == null) return;
                visit(node.left);
                visitor.visit(node.key, node.val);
                visit(node.right);
            }
        }
        new Closure().visit(root);
    }

    @FunctionalInterface
    public interface Visitor<K, V> {
        void visit(K key, V val);
    }

    /**
     * 右旋操作
     *
     * 旋转前：
     *          |
     *        node
     *       /
     *     rtd
     *
     * 旋转后：
     *         |
     *        rtd
     *           \
     *          node
     *
     * @param node 要旋转的节点
     * @return 旋转后的节点
     */
    private Node<K, V> rotateRight(Node<K, V> node) {
        var rotated = node.left;

        node.left = rotated.right;
        rotated.right = node;

        rotated.red = node.red;
        node.red = true;
        return rotated;
    }

    public static class Node<K, V> {
        K key;
        V val;
        Node<K, V> left, right, parent;
        boolean red;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }

        public Node(K key, V val, boolean red) {
            this.key = key;
            this.val = val;
            this.red = red;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", val=" + val +
                    '}';
        }
    }

    public static void main(String[] args) {
        var rbt = new RedBlackTree<Integer, Integer>();
        for (int i = 0; i < 100; ++i) {
            rbt.put0(i, i);
        }
        rbt.remove(99);
        rbt.forEach((k, v) -> System.out.printf("K: %s, v: %s\n", k, v));
    }

}
