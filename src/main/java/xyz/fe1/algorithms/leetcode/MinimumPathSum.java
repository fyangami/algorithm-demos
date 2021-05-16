package xyz.fe1.algorithms.leetcode;

/**
 * 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。

说明：每次只能向下或者向右移动一步。

 

示例 1：


输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
输出：7
解释：因为路径 1→3→1→1→1 的总和最小。
示例 2：

输入：grid = [[1,2,3],[4,5,6]]
输出：12
 

提示：

m == grid.length
n == grid[i].length
1 <= m, n <= 200
0 <= grid[i][j] <= 100
 */
public class MinimumPathSum {
    
    public static void main(String[] args) {
        System.out.println(minPathSum(new int[][]{{1,3,1},{1,5,1},{4,2,1}}));
    }

    /**
     * 动态规划
     *      到达(m, n)最小 = min((m - 1, n), (m, n - 1)) + (m, n)
     * @param grid
     * @return
     */
    public static int minPathSum(int[][] grid) {
        var dp = new int[grid.length][grid[0].length];
        int i, j;
        // base case
        dp[0][0] = grid[0][0];
        for (i = 1; i < grid.length; ++i) dp[i][0] = dp[i - 1][0] + grid[i][0];
        for (j = 1; j < grid[0].length; ++j) dp[0][j] = dp[0][j - 1] + grid[0][j];
        /**
         * dp generate
         */
        for (i = 1; i < grid.length; ++i) for (j = 1; j < grid[0].length; ++j) dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
        return dp[grid.length - 1][grid[0].length - 1];
    }
}
