package xyz.fe1.algorithms.leetcode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xyz.fe1.algorithms.sort.Sorts;

/**
 * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。请你合并所有重叠的区间，并返回一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间。

 

示例 1：

输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
输出：[[1,6],[8,10],[15,18]]
解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
示例 2：

输入：intervals = [[1,4],[4,5]]
输出：[[1,5]]
解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。
 

提示：

1 <= intervals.length <= 104
intervals[i].length == 2
0 <= starti <= endi <= 104
 */
public class MergeIntervals {
    
    public static void main(String[] args) {
        // var arr = new int[][]{{1, 3}, {2, 6}, {8, 10}, {11, 18}};
        var arr = new int[][]{{1, 4}, {2, 3}};
        System.out.println(Arrays.deepToString(merge(arr)));
    }

    /**
     * 先排序，再合并即可
     * @param intervals
     * @return
     */
    public static int[][] merge(int[][] intervals) {
        /**
         * 排序
         */
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        var merged = new ArrayList<int[]>();
        /**
         * merging 指向正在合并的数组， i指向当前数组（遍历intervals）
         */
        int merging, i;
        for (merging = 0, i = 1; i < intervals.length; ++i) {
            int[] curr = intervals[merging], next = intervals[i];
            if (curr[1] < next[0]) {
                merged.add(curr);
                merging = i;
            } else {
                curr[1] = next[1] > curr[1] ? next[1] : curr[1];
                next[0] = next[1] = 0;
            }
        }
        merged.add(intervals[merging]);
        return merged.toArray(new int[merged.size()][]);
    }
}
