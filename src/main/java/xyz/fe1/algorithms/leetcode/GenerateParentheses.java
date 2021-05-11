package xyz.fe1.algorithms.leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：n = 3
 * 输出：["((()))","(()())","(())()","()(())","()()()"]
 * 示例 2：
 *
 * 输入：n = 1
 * 输出：["()"]
 *  
 *
 * 提示：
 *
 * 1 <= n <= 8
 */
public class GenerateParentheses {


    public static void main(String[] args) {
        System.out.println(generateParenthesis(3));
    }

    /**
     * 深度优先遍历
     * @param n max length of parentheses
     * @return parentheses
     */
    public static List<String> generateParenthesis(int n) {
        var list = new LinkedList<String>();
        class Closure {
            void dfs(String generated, int open, int close) {
                if (generated.length() == n << 1) {
                    list.add(generated);
                } else {
                    /*
                     * 如果左括号的数量小于N，则可以继续放置
                     */
                    if (open < n) dfs(generated + "(", open + 1, close);
                    /*
                     * 如果右括号的数量小于左括号的数量，则可以继续放置
                     */
                    if (close < open) dfs(generated + ")", open, close + 1);
                }
            }
        }
        new Closure().dfs("", 0, 0);
        return list;
    }
}
