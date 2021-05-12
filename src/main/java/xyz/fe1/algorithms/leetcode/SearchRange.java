package xyz.fe1.algorithms.leetcode;

import java.util.Arrays;

/**
 * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
 *
 * 如果数组中不存在目标值 target，返回 [-1, -1]。
 *
 * 进阶：
 *
 * 你可以设计并实现时间复杂度为 O(log n) 的算法解决此问题吗？
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [5,7,7,8,8,10], target = 8
 * 输出：[3,4]
 * 示例 2：
 *
 * 输入：nums = [5,7,7,8,8,10], target = 6
 * 输出：[-1,-1]
 * 示例 3：
 *
 * 输入：nums = [], target = 0
 * 输出：[-1,-1]
 *  
 *
 * 提示：
 *
 * 0 <= nums.length <= 105
 * -109 <= nums[i] <= 109
 * nums 是一个非递减数组
 * -109 <= target <= 109
 */
public class SearchRange {

    public static void main(String[] args) {
        int[] arr = new int[]{2, 2};
        System.out.println(Arrays.toString(searchRange(arr, 2)));
    }

    /**
     * 二分查找
     * @param nums arr
     * @param target target
     * @return index range
     */
    public static int[] searchRange(int[] nums, int target) {
        int left = 0, right = nums.length - 1, mid;
        while (left <= right) {
            mid = (left + right) / 2;
            if (target > nums[mid]) left = mid + 1;
            else if (target < nums[mid]) right = mid - 1;
            else {
                /*
                 * 找到数字，开始遍历确定范围
                 */
                int begin = mid, end = mid;
                while (begin > 0 && nums[begin - 1] == target) begin--;
                while (end < nums.length - 1 && nums[end + 1] == target) end++;
                return new int[]{begin, end};
            }
        }
        return new int[]{-1, -1};
    }
}
