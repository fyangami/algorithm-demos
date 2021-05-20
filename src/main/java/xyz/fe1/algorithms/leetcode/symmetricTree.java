package xyz.fe1.algorithms.leetcode;

import xyz.fe1.datastruct.Stack;

/**
 * 给定一个二叉树，检查它是否是镜像对称的。
例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
    1
   / \
  2   2
 / \ / \
3  4 4  3
但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:

    1
   / \
  2   2
   \   \
   3    3
进阶：
你可以运用递归和迭代两种方法解决这个问题吗？
 */
public class symmetricTree {
    
    public static void main(String[] args) {
        var node = new TreeNode(1);
        System.out.println(isSymmetric(node));
    }

    /**
     * 递归解法:
     *      1. left == right
     *      2. left.left == right.right
     *      3. left.right == right.left
     *      同时满足以上三个条件即可
     * @param root
     * @return
     */
    public static boolean isSymmetric(TreeNode root) {
        class Closure {
            boolean valid(TreeNode left, TreeNode right) {
                if (left == right) return true; // left == right == null
                if (left == null || right == null) return false;  // left == null || right == null 不满足条件
                return left.val == right.val && valid(left.left, right.right) && valid(left.right, right.left);
            }
        }
        return new Closure().valid(root.left, root.right);
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
