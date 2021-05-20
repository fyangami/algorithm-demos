package xyz.fe1.algorithms.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。

 

示例：
二叉树：[3,9,20,null,null,15,7],

    3
   / \
  9  20
    /  \
   15   7
返回其层序遍历结果：

[
  [3],
  [9,20],
  [15,7]
]

 */
public class BinaryTreeLevelOrderTraversal {
    
    //[3,9,20,null,null,15,7]
    public static void main(String[] args) {
        var root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println(levelOrder0(root));
    }

    /**
     * 先序遍历，加个深度标识即可，深度直接当做list索引，将对应的节点插入对应的层级中
     *      tips: 有点取巧的意思，严格意义上不算层序遍历
     * @param root
     * @return
     */
    public static List<List<Integer>> levelOrder(TreeNode root) {
        var list = new ArrayList<List<Integer>>();
        class Closure {
            void visit(TreeNode root, int deep) {
                if (root == null) return;
                /**
                 * 填补list容量，防止索引溢出
                 */
                if (deep >= list.size())
                    for (int i = deep; i >= list.size(); --i) list.add(new ArrayList<Integer>());
                /**
                 * 将对应的节点放入对应的层级
                 */
                list.get(deep).add(root.val);
                visit(root.left, deep + 1);
                visit(root.right, deep + 1);
            }
        }
        new Closure().visit(root, 0);
        return list;
    }

    /**
     * 利用队列完成层序遍历
     *      tips: bfs 可以用队列，dfs可以用栈
     * @return
     */
    public static List<List<Integer>> levelOrder0(TreeNode root) {
        var list = new ArrayList<List<Integer>>();
        var queue = new LinkedList<TreeNode>();
        queue.add(root);
        while(queue.size() > 0) {
            var orderList = new ArrayList<Integer>();
            var levelSize = queue.size();
            for (int __ = 0; __ < levelSize; ++__) {
                var node = queue.poll();
                orderList.add(node.val);
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            list.add(orderList);
        }
        return list;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
