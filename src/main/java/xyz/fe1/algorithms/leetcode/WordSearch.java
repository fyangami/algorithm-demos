package xyz.fe1.algorithms.leetcode;

/**
 * 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。

单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。

 

示例 1：


输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
输出：true
示例 2：


输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
输出：true
示例 3：


输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
输出：false
 

提示：

m == board.length
n = board[i].length
1 <= m, n <= 6
1 <= word.length <= 15
board 和 word 仅由大小写英文字母组成

进阶：你可以使用搜索剪枝的技术来优化解决方案，使其在 board 更大的情况下可以更快解决问题？
 */
public class WordSearch {
    
    public static void main(String[] args) {
        var board = new char[][]{{ 'A', 'B', 'C', 'E' },{ 'S', 'F', 'C', 'S' },{ 'A', 'D', 'E', 'E' }};
        System.out.println(exist(board, "ABCB"));
        System.out.println(exist(board, "SEE"));
        System.out.println(exist(board, "ABCCED"));
        // var board = new char[][]{{ 'a', 'a', 'a', 'a' },{ 'a', 'a', 'a', 'a' },{ 'a', 'a', 'a', 'a' }};
        // System.out.println(exist(board, "aaaaaaaaaaaa"));
        // System.out.println(exist(board, "SEE"));
        // System.out.println(exist(board, "ABCCED"));
    }

    /**
     * 先找到匹配的起点，再递归查找
     * @param board
     * @param word
     * @return
     */
    public static boolean exist(char[][] board, String word) {
        int i, j;
        int[][] marked = new int[board.length][board[0].length];
        for (i = 0; i < board.length; ++i)
            for (j = 0; j < board[0].length; ++j)
                if (board[i][j] == word.charAt(0))
                    if (match(word, 1, board, i, j, marked)) return true;
        return false;
    }

    private static boolean match(String word, int charAt, char[][] board, int i, int j, int[][] marked) {
        /**
         * 标记已访问
         */
        marked[i][j] = 1;
        if (charAt == word.length()) return true;
        boolean match = false;
        var ch = word.charAt(charAt);
        /**
         * 向上找
         */
        if (i - 1 >= 0 && marked[i - 1][j] != 1)
            if (ch == board[i - 1][j]) match = match(word, charAt + 1, board, i - 1, j, marked);
        /**
         * 向右找
         */
        if (!match && j + 1 < board[0].length && marked[i][j + 1] != 1)
            if (ch == board[i][j + 1]) match = match(word, charAt + 1, board, i, j + 1, marked);
        /**
         * 向下找
         */
        if (!match && i + 1 < board.length && marked[i + 1][j] != 1)
            if (ch == board[i + 1][j]) match = match(word, charAt + 1, board, i + 1, j, marked);
        /**
         * 向左找
         */
        if (!match && j - 1 >= 0 && marked[i][j - 1] != 1)
            if (ch == board[i][j - 1]) match = match(word, charAt + 1, board, i, j - 1, marked);
        /**
         * 退出前重置标志
         */
        marked[i][j] = 0;
        return match;
    }
}
