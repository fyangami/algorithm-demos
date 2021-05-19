package xyz.fe1.algorithms.leetcode;

/**
 * 给你一个整数 n ，求恰由 n 个节点组成且节点值从 1 到 n 互不相同的 二叉搜索树 有多少种？返回满足题意的二叉搜索树的种数。

 

示例 1：


输入：n = 3
输出：5
示例 2：

输入：n = 1
输出：1
 

提示：

1 <= n <= 19
 */
public class UniqueBinarySearchTrees {

    public static void main(String[] args) {
        System.out.println(numTrees(3));
    }

    /**
     * 动态规划
     * 
     * @param n
     * @return
     */
    public static int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = dp[1] = 1;
        int i, j;
        for (i = 2; i <= n; ++i)
            for (j = 1; j <= i; ++j) dp[i] += dp[j - 1] * dp[i - j];
        return dp[n];
    }
    
}
