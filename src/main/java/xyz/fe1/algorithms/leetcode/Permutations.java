package xyz.fe1.algorithms.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 * 示例 2：
 *
 * 输入：nums = [0,1]
 * 输出：[[0,1],[1,0]]
 * 示例 3：
 *
 * 输入：nums = [1]
 * 输出：[[1]]
 *  
 *
 * 提示：
 *
 * 1 <= nums.length <= 6
 * -10 <= nums[i] <= 10
 * nums 中的所有整数 互不相同
 */
public class Permutations {

    public static void main(String[] args) {
        System.out.println(permute(new int[]{1, 2, 3}));
    }

    /**
     * 由于leetcode题目明确nums中数组在[-10, 10]中，这里借用Integer.MIN_VALUE 作为flag，标志这里的元素已经被访问过了
     * @param nums arr
     * @return list
     */
    public static List<List<Integer>> permute(int[] nums) {
        var list = new ArrayList<List<Integer>>();
        class Closure {
            void dfs(List<Integer> visited) {
                if (visited.size() == nums.length) {
                    list.add(List.copyOf(visited));
                } else {
                    for (var i = 0; i < nums.length; ++i) {
                        /*
                         * 暂存num
                         */
                        int tmp = nums[i];
                        /*
                         * 已访问过，忽略
                         */
                        if (tmp == Integer.MIN_VALUE) continue;
                        nums[i] = Integer.MIN_VALUE;
                        visited.add(tmp);
                        dfs(visited);
                        /*
                         * 恢复现场
                         */
                        visited.remove(visited.size() - 1);
                        nums[i] = tmp;
                    }
                }
            }
        }
        new Closure().dfs(new ArrayList<>());
        return list;
    }
}
