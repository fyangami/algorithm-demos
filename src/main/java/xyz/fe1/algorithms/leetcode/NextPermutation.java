package xyz.fe1.algorithms.leetcode;

import java.util.Arrays;

/**
 * 实现获取 下一个排列 的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。
 *
 * 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
 *
 * 必须 原地 修改，只允许使用额外常数空间。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [1,2,3]
 * 输出：[1,3,2]
 * 示例 2：
 *
 * 输入：nums = [3,2,1]
 * 输出：[1,2,3]
 * 示例 3：
 *
 * 输入：nums = [1,1,5]
 * 输出：[1,5,1]
 * 示例 4：
 *
 * 输入：nums = [1]
 * 输出：[1]
 *  
 *
 * 提示：
 *
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 100
 */
public class NextPermutation {

    public static void main(String[] args) {
        var arr = new int[]{1,5,1};
        nextPermutation(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * @param nums arr
     */
    public static void nextPermutation(int[] nums) {
        int tmp;
        /*
         * 从后向前找最小数（非递减列）
         */
        int minimum = nums.length - 1;
        while (minimum-- > 0) {
            if (nums[minimum] < nums[minimum + 1]) break;
        }
        /*
         * 在递减列中找到比最小数刚好大的数
         */
        if (minimum >= 0) {
            int sub = nums.length;
            while (sub-- > 0 && nums[sub] <= nums[minimum]);
            /*
             * 交换两个数
             */
            tmp = nums[sub];
            nums[sub] = nums[minimum];
            nums[minimum] = tmp;
        }
        /*
         * 重新排列数组
         */
        int tail = nums.length;
        while (++minimum < --tail) {
            tmp = nums[minimum];
            nums[minimum] = nums[tail];
            nums[tail] = tmp;
        }
    }


}
