/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class Ex39BoyerMooreStringSearch {

    private static final int R = 256;
    private final String pattern;
    private final int m;

    private final int[] badMatchTable;

    public Ex39BoyerMooreStringSearch(String pattern) {
        this.pattern = pattern;
        this.m = pattern.length();

        this.badMatchTable = new int[R];
        for (int i = 0; i < R; i++) badMatchTable[i] = -1;
        for (int i = 0; i < m; i++) badMatchTable[pattern.charAt(i)] = m - i;
    }

    public int search(String text) {
        int skip = 1;
        for (int i = m - 1; i < text.length(); i += skip) {
            for (int j = 0; j < m; j++) {
                if (pattern.charAt(m - 1 - j) != text.charAt(i - j)) {
                    skip = Math.max(1, badMatchTable[pattern.charAt(m - 1 - j)]);
                    break;
                }
                if (j == m - 1) return i - m + 1;
            }
        }
        return -1;
    }

    private static void test(String pattern, String text) {
        Ex39BoyerMooreStringSearch kmSearch = new Ex39BoyerMooreStringSearch(pattern);
        StringBuilder builder = new StringBuilder();
        int index = kmSearch.search(text);
        if (index > -1) {
            builder.append(text);
            builder.insert(index + pattern.length(), '*');
            builder.insert(index, '*');
        }
        else builder.append("not found");
        StdOut.println("search for " + pattern + " in " + text + ": " + builder.toString());
    }

    public static void main(String[] args) {
        test("ABRA", "ABACADABRAC");
        test("BAAAAAAAAA", "ABAAAABAAAAAAAAA");
        test("BAAAABAAAA", "ABAAAABAAAAAAAAA");
        test("BAAABAAAA", "ABAAAABAAAAAAAAA");
        test("NEEDLE", "THIS IS THE NEED TO EEDLE FIND THE NEEDLE IN A HAY STACK");
    }
}
