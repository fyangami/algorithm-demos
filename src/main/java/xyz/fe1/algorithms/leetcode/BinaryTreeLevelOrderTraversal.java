package xyz.fe1.algorithms.leetcode;

import java.util.ArrayList;
import java.util.List;

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
    

    public static void main(String[] args) {
        
    }

    /**
     * 先序遍历，加个深度标识即可，深度直接当做list索引，将对应的节点插入对应的层级中
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
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

    public class TreeNode {
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
