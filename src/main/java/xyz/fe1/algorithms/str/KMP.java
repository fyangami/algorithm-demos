package xyz.fe1.algorithms.str;

public class KMP {

    private final int[][] dfa;
    private final String pattern;

    /**
     * 构建状态机的过程
     * @param pattern 模板字符串（子串）
     */
    public KMP(String pattern) {
        this.pattern = pattern;
        var pl = pattern.length();
        dfa = new int[256][pl];
        dfa[pattern.charAt(0)][0] = 1;
        int i, j, k;
        for (i = 0, j = 1; j < pl; ++j) {
            for (k = 0; k < 256; ++k) dfa[k][j] = dfa[k][i];
            dfa[pattern.charAt(j)][j] = j + 1;
            i = dfa[pattern.charAt(j)][i];
        }
    }

    /**
     * 匹配过程，循环比较晦涩，当 j == pl 时，表示已经匹配到了完整的字符串，可以结束了
     * @param source 源字符串
     * @return 子串的起始下标，未匹配到返回-1
     */
    public int search(String source) {
        int i, j;
        int pl = pattern.length(), sl = source.length();
        for (i = 0, j = 0; i < sl && j < pl; ++i) j = dfa[source.charAt(i)][j];
        if (j == pl) return i - pl;
        return -1;
    }

    public String getPattern() {
        return pattern;
    }

    public static void main(String[] args) {
        var kmp = new KMP("fyangami");
        var source = "fyangfyangfffayyyyfafhjaskhd-fyangami-dasyudgasdasdashuifhsajdhjk";
        System.out.println(kmp.search(source));
        System.out.println(source.indexOf("fyangami"));

    }
}
