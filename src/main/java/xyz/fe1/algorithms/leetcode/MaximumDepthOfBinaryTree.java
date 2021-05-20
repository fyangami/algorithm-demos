package xyz.fe1.algorithms.leetcode;

import java.util.Stack;

/**
 * 给定一个二叉树，找出其最大深度。

二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。

说明: 叶子节点是指没有子节点的节点。

示例：
给定二叉树 [3,9,20,null,null,15,7]，

    3
   / \
  9  20
    /  \
   15   7
返回它的最大深度 3 。
 */
public class MaximumDepthOfBinaryTree {
   
    
    public static void main(String[] args) {
        var root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println(maxDepth0(root));
    }

    /**
     * 递归解法，任意遍历顺序即可
     * @param root
     * @return
     */
    public static int maxDepth0(TreeNode root) {
        int[] maxDepth = { 0 };
        class Closure {
            void visit(TreeNode node, int depth) {
                maxDepth[0] = Math.max(maxDepth[0], depth);
                if (node == null) return;
                visit(node.left, depth + 1);
                visit(node.right, depth + 1);
            }
        }
        new Closure().visit(root, 0);
        return maxDepth[0];
    }

    /**
     * 非递归中序遍历, currentDepth 记录当前深度
     * @param root
     * @return
     */
    public static int maxDepth(TreeNode root) {
        int maxDepth = 0, currentDepth = 1;
        var stack = new Stack<TreeNode>();
        do {
            while(root != null) {
                stack.push(root);
                root = root.left;
                currentDepth++;
            }
            maxDepth = Math.max(maxDepth, currentDepth);
            root = stack.pop();
            currentDepth--;
            root = root.right;
        } while (stack.size() > 0 || root != null);
        return maxDepth;
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
