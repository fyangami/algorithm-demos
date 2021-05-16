package xyz.fe1.algorithms.leetcode;

import java.util.Arrays;

/**
 * 给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。

此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。

 

示例 1：

输入：nums = [2,0,2,1,1,0]
输出：[0,0,1,1,2,2]
示例 2：

输入：nums = [2,0,1]
输出：[0,1,2]
示例 3：

输入：nums = [0]
输出：[0]
示例 4：

输入：nums = [1]
输出：[1]
 

提示：

n == nums.length
1 <= n <= 300
nums[i] 为 0、1 或 2
 

进阶：

你可以不使用代码库中的排序函数来解决这道题吗？
你能想出一个仅使用常数空间的一趟扫描算法吗？
 */
public class SortColors {
    
    public static void main(String[] args) {
        int[] arr = {2, 0, 2, 1, 1, 0};
        sortColors(arr);
        System.out.println(Arrays.toString(arr));
    }
    
    /**
     * 基本思路：
     *      碰到红色放在数组头部，碰到蓝色放在数组尾部，碰到白色跳过
     * @param nums
     */
    public static void sortColors(int[] nums) {
        if (nums.length == 1) return;
        int RED = 0, BLUE = 2;
        int redCursor = 0, blueCursor = nums.length - 1, whiteCursor;
        for (whiteCursor = 0;;) {
            if (nums[whiteCursor] == RED) {
                while(redCursor < nums.length && nums[redCursor] == RED) redCursor++;
                if (whiteCursor > redCursor) swap(nums, whiteCursor, redCursor);
                else whiteCursor++;
            } else if (nums[whiteCursor] == BLUE) {
                while(blueCursor > 0 && nums[blueCursor] == BLUE) blueCursor--;
                if (whiteCursor < blueCursor) swap(nums, whiteCursor, blueCursor);
                else whiteCursor++;
            } else {
                whiteCursor++;
            }
            if (whiteCursor > blueCursor) break;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        var tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
