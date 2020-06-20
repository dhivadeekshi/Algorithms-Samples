/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class Ex30MSDRadixSort {

    private static final int CUTOFF = 10;

    public static void sort(String[] s) {
        String[] aux = new String[s.length];
        sort(s, aux, 0, 0, s.length - 1);
    }

    private static void sort(String[] s, String[] aux, int d, int lo, int hi) {
        if (hi <= lo) return;

        if (hi - lo + 1 < CUTOFF) {
            for (int i = lo; i <= hi; i++) {
                for (int j = i; j > lo; j--) {
                    if (less(s[j], s[j - 1], d)) exch(s, j, j - 1);
                }
            }
            return;
        }

        int R = 256;
        int[] count = new int[R + 2];

        for (int i = lo; i <= hi; i++)
            count[charAt(s[i], d) + 2]++;

        for (int i = 0; i < R; i++)
            count[i + 1] += count[i];

        for (int i = lo; i <= hi; i++)
            aux[lo + count[charAt(s[i], d) + 1]++] = s[i];

        for (int i = lo; i <= hi; i++)
            s[i] = aux[i];

        for (int i = 0; i < R; i++) {
            sort(s, aux, d + 1, lo + count[i], lo + count[i + 1] - 1);
        }
    }

    private static int charAt(String s, int i) {
        if (i < s.length()) return s.charAt(i);
        return -1;
    }

    private static boolean less(String a, String b, int d) {
        return a.substring(d).compareTo(b.substring(d)) < 0;
    }

    private static void exch(String[] s, int i, int j) {
        String temp = s[i];
        s[i] = s[j];
        s[j] = temp;
    }

    private static boolean isSorted(String[] s) {
        for (int i = 1; i < s.length; i++)
            if (less(s[i], s[i - 1], 0)) return false;
        return true;
    }

    public static void main(String[] args) {
        int n = StdRandom.uniform(5, 1000000);
        String[] test = new String[n];
        StdOut.println("Original:");
        int maxLength = 50;
        for (int i = 0; i < n; i++) {

            int len = StdRandom.uniform(3, 20);
            StringBuilder builder = new StringBuilder();
            if (i > 1 && StdRandom.bernoulli()) {
                int ri = StdRandom.uniform(i);
                builder.append(test[ri]);
                len = Math.min(maxLength, StdRandom.uniform(5) + test[ri].length());
            }

            for (int j = 0; j < len; j++) {
                if ((j > 0 || i > 0) && StdRandom.bernoulli()) {
                    if (j > 0) builder.append(builder.charAt(j - 1));
                    else builder.append(test[i - 1].charAt(0));
                }
                else {
                    int ci = StdRandom.uniform('a', 'z' + 1);
                    builder.append((char) ci);
                }
            }
            test[i] = builder.toString();
            StdOut.println(builder.toString());
        }

        Stopwatch stopwatch = new Stopwatch();
        sort(test);
        double timeElapsed = stopwatch.elapsedTime();

        StdOut.println();
        StdOut.println("Sorted:");
        for (String s : test)
            StdOut.println(s);

        StdOut.println();
        StdOut.println("n:" + n + " elapsed time : " + timeElapsed);
        StdOut.println("isSorted? " + isSorted(test));
    }
}
