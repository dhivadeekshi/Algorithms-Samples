/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Ex29LSDRadixSort {
    // REQUIREMENT: all keys should be of equal length

    public static void sort(String[] s, int W) {
        int R = 256;
        int n = s.length;
        int d = W;
        String[] aux = new String[n];
        while (--d >= 0) {
            int[] count = new int[R + 1];
            for (int i = 0; i < n; i++)
                count[s[i].charAt(d) + 1]++;

            for (int i = 0; i < R; i++)
                count[i + 1] += count[i];

            for (int i = 0; i < n; i++)
                aux[count[s[i].charAt(d)]++] = s[i];

            for (int i = 0; i < n; i++)
                s[i] = aux[i];
        }
    }

    public static void main(String[] args) {
        int n = StdRandom.uniform(3, 10);
        int len = StdRandom.uniform(3, 10);
        String[] test = new String[n];
        StdOut.println("Original:");
        for (int i = 0; i < n; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < len; j++) {
                int ci = StdRandom.uniform('a', 'z' + 1);
                builder.append((char) ci);
            }
            test[i] = builder.toString();
            StdOut.println(builder.toString());
        }

        sort(test, len);
        StdOut.println();
        StdOut.println("Sorted:");
        for (String s : test)
            StdOut.println(s);
    }
}
