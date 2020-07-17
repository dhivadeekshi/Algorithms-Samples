/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class Ex31ThreeWayRadixSort {

    public static void sort(String[] s) {
        sort(s, 0, s.length - 1, 0);
    }

    private static void sort(String[] s, int lo, int hi, int d) {
        if (hi <= lo) return;
        int lt = lo;
        int gt = hi;
        int v = charAt(s[lo], d);
        int k = lo;
        while (k <= gt) {
            if (charAt(s[k], d) < v) exch(s, k++, lt++);
            else if (charAt(s[k], d) > v) exch(s, k, gt--);
            else k++;
        }

        sort(s, lo, lt - 1, d);
        if (v >= 0) sort(s, lt, gt, d + 1);
        sort(s, gt + 1, hi, d);
    }

    private static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d);
        return -1;
    }

    private static boolean less(String a, String b) {
        return a.compareTo(b) < 0;
    }

    private static void exch(String[] s, int i, int j) {
        String temp = s[i];
        s[i] = s[j];
        s[j] = temp;
    }

    private static boolean isSorted(String[] s) {
        for (int i = 1; i < s.length; i++)
            if (less(s[i], s[i - 1])) return false;
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
