/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.Queue;

public class As5BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {

        String s = BinaryStdIn.readString();
        int n = s.length();
        As5CircularSuffixArray csa = new As5CircularSuffixArray(s);
        char[] result = new char[n];
        int start = 0;
        for (int i = 0; i < n; i++) {
            int index = csa.index(i);
            if (index == 0) {
                start = i;
                result[i] = s.charAt(n - 1);
            }
            else result[i] = s.charAt(index - 1);
        }

        BinaryStdOut.write(start);
        for (int i = 0; i < n; i++)
            BinaryStdOut.write(result[i]);
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String inputString = BinaryStdIn.readString();
        int n = inputString.length();

        char[] t = inputString.toCharArray();
        char[] sorted = inputString.toCharArray();
        sort(sorted, 0, n - 1);

        int R = 256;
        Queue<Integer>[] table = (Queue<Integer>[]) new Queue[R];
        for (int i = 0; i < n; i++) {
            int ch = t[i];
            if (table[ch] == null) table[ch] = new Queue<>();
            table[ch].enqueue(i);
        }

        int[] next = new int[n];
        for (int i = 0; i < n; i++)
            next[i] = table[sorted[i]].dequeue();

        /* int[] next = new int[n];
        int index = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0 || sorted[i] != sorted[i - 1]) index = 0;
            while (sorted[i] != t[index]) index++;
            next[i] = index++;
        }*/

        int j = first;
        for (int i = 0; i < n; i++) {
            BinaryStdOut.write(sorted[j]);
            j = next[j];
        }

        BinaryStdOut.close();
    }

    private static void sort(char[] a, int lo, int hi) {
        if (hi <= lo) return;

        char c = a[lo];
        int lt = lo, gt = hi, i = lo + 1;
        while (i <= gt) {
            char v = a[i];
            if (v < c) exch(a, lt++, i++);
            else if (v > c) exch(a, i, gt--);
            else i++;
        }

        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    private static void exch(char[] a, int i, int j) {
        char temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
        else throw new IllegalArgumentException();
    }
}
