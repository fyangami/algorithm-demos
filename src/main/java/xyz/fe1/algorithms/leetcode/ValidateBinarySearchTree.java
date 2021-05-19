package xyz.fe1.algorithms.leetcode;


/**
 * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。

假设一个二叉搜索树具有如下特征：

节点的左子树只包含小于当前节点的数。
节点的右子树只包含大于当前节点的数。
所有左子树和右子树自身必须也是二叉搜索树。
示例 1:

输入:
    2
   / \
  1   3
输出: true
示例 2:

输入:
    5
   / \
  1   4
     / \
    3   6
输出: false
解释: 输入为: [5,1,4,null,null,3,6]。
     根节点的值为 5 ，但是其右子节点值为 4 。
 */
public class ValidateBinarySearchTree {
   
    public static void main(String[] args) {
        TreeNode node = new TreeNode();
        node.val = 0;
        TreeNode node1 = new TreeNode();
        node1.val = -1;
        node.left = node1;
        System.out.println(isValidBST(node));
    }

    static int prev = Integer.MIN_VALUE;
    public static boolean isValidBST(TreeNode root) {
        if (root == null) return false;
        class Closure {
            boolean visit(TreeNode root) {
                if (root == null) return true;
                if (!visit(root.left)) return false;
                if (prev > root.val) return false;
                prev = root.val;
                if (!visit(root.right)) return false;
                return true;
            }
        }
        return new Closure().visit(root);
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
