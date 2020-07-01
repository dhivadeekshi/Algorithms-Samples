/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class As5CircularSuffixArray {

    private static final int CUTOFF = 15;

    private final int[] index;
    private final int n;

    // circular suffix array of s
    public As5CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException("String cannot be null");

        this.n = s.length();
        this.index = new int[n];
        for (int i = 0; i < n; i++) index[i] = i;
        sort(s, 0, n - 1, 0);
    }

    private void sort(String s, int lo, int hi, int d) {
        if (d > n - 1) return;
        if (hi <= lo) return;
        if (hi - lo + 1 <= CUTOFF) {
            // insertion sort
            insertionSort(s, lo, hi, d);
            return;
        }

        // three way string sort
        char ch = charAt(s, index[lo] + d);
        int lt = lo, gt = hi, k = lo + 1;
        while (k <= gt) {
            char v = charAt(s, index[k] + d);
            if (v < ch) exch(lt++, k++);
            else if (v > ch) exch(gt--, k);
            else k++;
        }

        sort(s, lo, lt - 1, d);
        sort(s, lt, gt, d + 1);
        sort(s, gt + 1, hi, d);
    }

    private char charAt(String s, int i) {
        return s.charAt(i % n);
    }

    private void insertionSort(String s, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo; j--)
                if (less(s, index[j], index[j - 1], d)) exch(j, j - 1);
                else break;
        }
    }

    private boolean less(String s, int a, int b, int d) {
        for (int i = d; i < n; i++) {
            char ca = charAt(s, a + i);
            char cb = charAt(s, b + i);
            if (ca > cb) return false;
            else if (ca < cb) return true;
        }
        return false;
    }

    private void exch(int i, int j) {
        int temp = index[i];
        index[i] = index[j];
        index[j] = temp;
    }

    // length of s
    public int length() {
        return n;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= n) throw new IllegalArgumentException("index out of range");
        return index[i];
    }

    private static boolean isSorted(As5CircularSuffixArray csa, String s) {
        for (int i = 1; i < csa.length(); i++) {
            int currIndex = csa.index(i);
            int prevIndex = csa.index(i - 1);
            String curr = s.substring(currIndex) + s.substring(0, currIndex);
            String prev = s.substring(prevIndex) + s.substring(0, prevIndex);
            if (curr.compareTo(prev) < 0) {
                StdOut.println("Mismatch @ " + i);
                StdOut.println("prev:" + prev);
                StdOut.println("curr:" + curr);
                return false;
            }
        }
        return true;
    }

    private static String readString(String input, int n) {
        BinaryIn in = new BinaryIn(input);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++)
            builder.append(in.readChar());
        return builder.toString();
    }

    // unit testing (required)
    public static void main(String[] args) {
        BinaryIn in = new BinaryIn("abra.txt");
        String input = in.readString();
        StdOut.println(input);
        Stopwatch watch = new Stopwatch();
        As5CircularSuffixArray csa = new As5CircularSuffixArray(input);
        StdOut.println("length:" + csa.length() + " elapsed:" + watch.elapsedTime());

        for (int i = 0; i < csa.length(); i++)
            StdOut.println(i + ": " + csa.index(i) + " " + input.substring(csa.index(i)) +
                                   input.substring(0, csa.index(i)));

        StdOut.println("isSorted? " + isSorted(csa, input));
    }
}
