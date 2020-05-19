/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class Ex5ThreeSum {

    private static int countUsingBruteForce(int[] numbers) {
        int count = 0;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                for (int k = j + 1; k < numbers.length; k++) {
                    if (numbers[i] + numbers[j] + numbers[k] == 0)
                        count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {

        int n = 10000; // StdRandom.uniform(5, 1000);
        int[] numbers = new int[n];
        StdOut.print("[");
        for (int i = 1; i <= n; i++) {
            numbers[i - 1] = StdRandom.uniform(-100, 100);
            StdOut.print(numbers[i - 1]);
            if (i < n) {
                StdOut.print(",");
                if (i % 100 == 0) StdOut.println();
            }
        }
        StdOut.println("]");


        Stopwatch stopwatch = new Stopwatch();
        StdOut.println("countUsingBruteForce : " + countUsingBruteForce(numbers));
        StdOut.println("Computed in : " + stopwatch.elapsedTime());
    }
}
