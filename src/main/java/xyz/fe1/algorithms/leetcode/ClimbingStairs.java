package xyz.fe1.algorithms.leetcode;

/**
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。

每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？

注意：给定 n 是一个正整数。

示例 1：

输入： 2
输出： 2
解释： 有两种方法可以爬到楼顶。
1.  1 阶 + 1 阶
2.  2 阶
示例 2：

输入： 3
输出： 3
解释： 有三种方法可以爬到楼顶。
1.  1 阶 + 1 阶 + 1 阶
2.  1 阶 + 2 阶
3.  2 阶 + 1 阶
 */
public class ClimbingStairs {
    public static void main(String[] args) {
        System.out.println(climbStairs0(44));
    }

    /**
     * 动态规划
     *      dp[n] = dp[n - 1] + dp[n - 2]
     * 
     *      dp[1] = 1 即可改成斐波那契数列
     * @param n
     * @return
     */
    public static int climbStairs0(int n) {
        if (n == 1) return 1;
        var dp = new int[n];
        /**
         * base case
         */
        dp[0] = 1;
        dp[1] = 2;
        int i;
        /**
         * dp generate
         */
        for (i = 2; i < n; ++i) dp[i] = dp[i - 1] + dp[i - 2];
        return dp[n - 1];
    }

    /**
     * 超时 ):
     * @param n
     * @return
     */
    public static int climbStairs(int n) {
        class Closure {
            int dfs(int surplus) {
                if (surplus == 0) return 1;
                var count = 0;
                if (surplus == 1) count += dfs(surplus - 1);
                else {
                    count += dfs(surplus - 1);
                    count += dfs(surplus - 2);
                }
                return count;
            }
        }
        return new Closure().dfs(n);
    }
}
