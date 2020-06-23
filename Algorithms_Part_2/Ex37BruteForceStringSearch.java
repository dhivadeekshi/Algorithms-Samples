/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Ex37BruteForceStringSearch {

    public static int search(String pattern, String text) {
        int n = text.length();
        int m = pattern.length();

        for (int i = 0; i <= n - m; i++) {
            for (int j = 0; j < m; j++) {
                if (text.charAt(j + i) != pattern.charAt(j)) break;
                if (j == m - 1) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int search(String pattern, In in) {
        return search(pattern, in.readAll());
    }

    private static void test(String pattern, String text) {
        StringBuilder builder = new StringBuilder();
        int index = search(pattern, text);
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
