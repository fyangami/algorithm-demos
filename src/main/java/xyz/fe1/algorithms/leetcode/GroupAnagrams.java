package xyz.fe1.algorithms.leetcode;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * 给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。
 *
 * 示例:
 *
 * 输入: ["eat", "tea", "tan", "ate", "nat", "bat"]
 * 输出:
 * [
 *   ["ate","eat","tea"],
 *   ["nat","tan"],
 *   ["bat"]
 * ]
 * 说明：
 *
 * 所有输入均为小写字母。
 * 不考虑答案输出的顺序。
 */
public class GroupAnagrams {

    public static void main(String[] args) {
        System.out.println(groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}));
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        var map = new HashMap<String, List<String>>();
        for (var str: strs) {
            var chars = str.toCharArray();
            Arrays.sort(chars);
            var k = new String(chars);
            var strings = map.get(new String(chars));
            if (strings == null) {
                strings = new LinkedList<>();
                map.put(k, strings);
            }
            strings.add(str);
        }
        return new ArrayList<>(map.values());
    }
}
