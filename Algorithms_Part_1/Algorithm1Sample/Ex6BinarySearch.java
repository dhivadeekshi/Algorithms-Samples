/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Ex6BinarySearch {

    private static int find(int[] a, int i) {
        Arrays.sort(a);
        int lo = 0;
        int hi = a.length;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (i < a[mid]) hi = mid - 1;
            else if (i > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    public static void main(String[] args) {
        int n = 100;
        int[] value = new int[n];
        StdOut.print("[");
        for (int i = 1; i <= n; i++) {
            value[i - 1] = StdRandom.uniform(-100, 100);
            StdOut.print(value[i - 1]);
            if (i < n) {
                StdOut.print(",");
                if (i % 50 == 0) StdOut.println();
            }
        }
        StdOut.println("]");

        int testCases = StdRandom.uniform(n / 4);
        for (int i = 0; i < testCases; i++) {
            int test = StdRandom.uniform(-100, 100);
            int index = Ex6BinarySearch.find(value, test);
            StdOut.println("Find " + test + ":" + (index == -1 ? " Not Found" : index));
        }
    }
}
