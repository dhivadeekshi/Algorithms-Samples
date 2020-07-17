/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Ex38KnuthMorrisStringSearch {

    private static final int R = 256;
    private final String pattern;
    private final int[][] pfa;
    private final int m;

    public Ex38KnuthMorrisStringSearch(String pattern) {
        this.pattern = pattern;
        this.m = pattern.length();

        this.pfa = new int[R][m];
        this.pfa[pattern.charAt(0)][0] = 1;
        int x = 0;
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < R; j++)
                this.pfa[j][i] = this.pfa[j][x];
            this.pfa[pattern.charAt(i)][i] = i + 1;
            x = this.pfa[pattern.charAt(i)][x];
        }
    }

    public int search(String text) {
        for (int i = 0, j = 0; i < text.length(); i++) {
            j = pfa[text.charAt(i)][j];
            if (j == m) return i - m + 1;
        }
        return -1;
        // return search(new In(text));
    }

    public int search(In in) {
        for (int i = 0, j = 0; !in.isEmpty(); i++) {
            j = pfa[in.readChar()][j];
            if (j == m) return i - m + 1;
        }
        return -1;
    }

    public String pattern() {
        return pattern;
    }

    private static void test(String pattern, String text) {
        Ex38KnuthMorrisStringSearch kmSearch = new Ex38KnuthMorrisStringSearch(pattern);
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
    }
}
