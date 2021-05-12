package xyz.fe1.algorithms.leetcode;

/**
 * 整数数组 nums 按升序排列，数组中的值 互不相同 。
 *
 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
 *
 * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [4,5,6,7,0,1,2], target = 0
 * 输出：4
 * 示例 2：
 *
 * 输入：nums = [4,5,6,7,0,1,2], target = 3
 * 输出：-1
 * 示例 3：
 *
 * 输入：nums = [1], target = 0
 * 输出：-1
 *  
 *
 * 提示：
 *
 * 1 <= nums.length <= 5000
 * -10^4 <= nums[i] <= 10^4
 * nums 中的每个值都 独一无二
 * 题目数据保证 nums 在预先未知的某个下标上进行了旋转
 * -10^4 <= target <= 10^4
 *  
 *
 * 进阶：你可以设计一个时间复杂度为 O(log n) 的解决方案吗？
 */
public class SearchSpinArray {

    public static void main(String[] args) { // target: 0  midVal: 1
        int[] arr = new int[]{6, 7, 0, 1, 2, 3, 4, 5};
//        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        System.out.println(search0(arr, 0));
        System.out.println(search0(arr, 6));
        System.out.println(search0(arr, 7));
        System.out.println(search0(arr, 1));
        System.out.println(search0(arr, 2));
        System.out.println(search0(arr, 3));
        System.out.println(search0(arr, 4));
        System.out.println(search0(arr, 5));
        System.out.println(search0(arr, 9));
    }

    /**
     * 改进二分查找
     *      记录第一个元素，与mid比较确定旋转序列位置
     * @param nums arr
     * @param target target
     * @return index
     */
    public static int search0(int[] nums, int target) {
        if (nums.length == 0) return -1;
        else if (nums.length == 1) return nums[0] == target ? 0 : -1;
        else {
            int left = 0, right = nums.length - 1, mid;
            var first = nums[left];
            var last = nums[right];
            while (left <= right) {
                mid = (left + right) / 2;
                var test = nums[mid];
                if (target == test) return mid;
                /*
                 * 先判断数组哪里有序
                 */
                if (first <= test) {
                    /*
                     * 前半段有序的
                     */
                    if (first <= target && target <= test) right = mid - 1;
                    else left = mid + 1;
                } else {
                    /*
                     * 后半段有序
                     */
                    if (test < target && target <= last) left = mid + 1;
                    else right = mid - 1;
                }
            }
        }
        return -1;
    }

    /**
     * 二分查找法, 仅针对有序数组
     * @param nums arr
     * @param target target
     * @return index
     */
    public static int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1, mid;
        while (left <= right) {
            mid = (left + right) / 2;
            if (target > nums[mid]) left = mid + 1;
            else if (target < nums[mid]) right = mid - 1;
            else return mid;

        }
        return -1;
    }

}
