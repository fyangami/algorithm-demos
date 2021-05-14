package xyz.fe1.algorithms.leetcode;

/**
 * 给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。

数组中的每个元素代表你在该位置可以跳跃的最大长度。

判断你是否能够到达最后一个下标。

 

示例 1：

输入：nums = [2,3,1,1,4]
输出：true
解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。
示例 2：

输入：nums = [3,2,1,0,4]
输出：false
解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ， 所以永远不可能到达最后一个下标。
 

提示：

1 <= nums.length <= 3 * 104
0 <= nums[i] <= 105

 */
public class JumpGame {
    
    public static void main(String[] args) {
        System.out.println(canJump0(new int[]{3,2,1,0,4}));
    }

    /**
     * 贪心算法，每次都去最大步长
     * @param nums
     * @return
     */
    public static boolean canJump(int[] nums) {
        var jumped = 0;
        for (var i = 0; i < nums.length; ++i) {
            if (i <= jumped) {
                jumped = Math.max(jumped, i + nums[i]);
                if (jumped >= nums.length - 1) return true;
            }
        }
        return false;
    }

    /**
     * 逆向思维：判断0能否被越过，无法越过则表明无法到达终点
     * @param nums
     * @return
     */
    public static boolean canJump0(int[] nums) {
        int i, j;
        for (i = 0; i < nums.length; ++i) {
            if (nums[i] == 0) {
                for(j = i - 1; j >=0; --j) {
                    if (j + nums[j] > i) break;
                }
                if (j < 0) return false;
            }
        }
        return true;
    }
}
