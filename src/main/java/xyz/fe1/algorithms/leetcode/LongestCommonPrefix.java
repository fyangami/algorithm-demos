package xyz.fe1.algorithms.leetcode;

/**
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 *
 * 如果不存在公共前缀，返回空字符串 ""。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：strs = ["flower","flow","flight"]
 * 输出："fl"
 * 示例 2：
 *
 * 输入：strs = ["dog","racecar","car"]
 * 输出：""
 * 解释：输入不存在公共前缀。
 *  
 *
 * 提示：
 *
 * 0 <= strs.length <= 200
 * 0 <= strs[i].length <= 200
 * strs[i] 仅由小写英文字母组成
 */
public class LongestCommonPrefix {


    public static void main(String[] args) {
        System.out.println(longestCommonPrefix(new String[]{"flower", "flow", "flight"}));
        System.out.println(longestCommonPrefix(new String[]{"cir", "car"}));
    }

    /**
     * 可用二分查找求解，能减少比较次数，需计算查找范围，即：[0, 最短字符串]
     * @param strs sources
     * @return common prefix
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        int i, j;
        var prefix = new StringBuilder();
        for (i = 0; ; ++i) {
            char match = 0;
            for (j = 0; j < strs.length; ++j) {
                if (i >= strs[j].length()) break;
                if (j == 0) match = strs[j].charAt(i);
                if (strs[j].charAt(i) != match) break;
            }
            if (j == strs.length) prefix.append(match);
            else break;
        }
        return prefix.toString();
    }
}
