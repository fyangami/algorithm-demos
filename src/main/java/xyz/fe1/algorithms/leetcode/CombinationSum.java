package xyz.fe1.algorithms.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 *
 * candidates 中的数字可以无限制重复被选取。
 *
 * 说明：
 *
 * 所有数字（包括 target）都是正整数。
 * 解集不能包含重复的组合。 
 * 示例 1：
 *
 * 输入：candidates = [2,3,6,7], target = 7,
 * 所求解集为：
 * [
 *   [7],
 *   [2,2,3]
 * ]
 * 示例 2：
 *
 * 输入：candidates = [2,3,5], target = 8,
 * 所求解集为：
 * [
 *   [2,2,2,2],
 *   [2,3,3],
 *   [3,5]
 * ]
 *  
 *
 * 提示：
 *
 * 1 <= candidates.length <= 30
 * 1 <= candidates[i] <= 200
 * candidate 中的每个元素都是独一无二的。
 * 1 <= target <= 500
 */
public class CombinationSum {

    public static void main(String[] args) {
        int[] arr = {2, 3, 6, 7};
        System.out.println(combinationSum(arr, 7));
    }

    /**
     * 直接回溯，若 sum > target 停止回溯
     * @param candidates arr
     * @param target target
     * @return ans
     */
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        var list = new ArrayList<List<Integer>>();
        class Closure {
            void dfs(List<Integer> visited, int begin, int surplus) {
                if (surplus == 0) {
                    list.add(List.copyOf(visited));
                } else if (surplus > 0) {
                    int i;
                    for (i = begin; i < candidates.length; ++i) {
                        visited.add(candidates[i]);
                        dfs(visited, i, surplus - candidates[i]);
                        visited.remove(visited.size() - 1);
                    }
                }
            }
        }
        new Closure().dfs(new ArrayList<>(), 0, target);
        return list;
    }
}
