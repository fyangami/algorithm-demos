package xyz.fe1.algorithms.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。

解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。

 

示例 1：

输入：nums = [1,2,3]
输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
示例 2：

输入：nums = [0]
输出：[[],[0]]
 

提示：

1 <= nums.length <= 10
-10 <= nums[i] <= 10
nums 中的所有元素 互不相同
 */
public class SubSets {
    
    public static void main(String[] args) {
        System.out.println(subsets(new int[]{1, 2, 3}));
    }

    /**
     * 递归，终于没超时 (:
     * @param nums
     * @return
     */
    public static List<List<Integer>> subsets(int[] nums) {
        var list = new LinkedList<List<Integer>>();
        list.add(new ArrayList<Integer>());
        class Closure {
            void dfs(List<Integer> visit, int cursor) {
                for (; cursor < nums.length; ++cursor) {
                    visit.add(nums[cursor]);
                    list.add(List.copyOf(visit));
                    dfs(visit, cursor + 1);
                    visit.remove(visit.size() - 1);
                }
            }
        }
        new Closure().dfs(new ArrayList<Integer>(), 0);
        return list;
    }
}
