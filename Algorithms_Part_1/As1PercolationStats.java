/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class As1PercolationStats {

    private final double[] result;
    private final double nTrials;

    // perform independent trials on an n-by-n grid
    public As1PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        nTrials = trials;
        result = new double[trials];
        for (int i = 0; i < trials; i++) {
            As1Percolation pUF = new As1Percolation(n);
            int[] indexes = new int[n * n];
            for (int j = 0; j < indexes.length; j++)
                indexes[j] = j;
            StdRandom.shuffle(indexes);
            int index = 0;
            while (!pUF.percolates()) {
                int row = (indexes[index] / n) + 1;
                int col = (indexes[index] % n) + 1;
                index++;
                pUF.open(row, col);
            }
            result[i] = (double) pUF.numberOfOpenSites() / (double) indexes.length;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(result);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(result);
    }

    private double confidence() {
        return ((1.96 * stddev()) / Math.sqrt(nTrials));
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - confidence();
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + confidence();
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        As1PercolationStats ps = new As1PercolationStats(n, trials);

        StdOut.printf("mean                     = %f\n", ps.mean());
        StdOut.printf("stddev                   = %f\n", ps.stddev());
        StdOut.print("95% confidence interval = [");
        StdOut.print(ps.confidenceLo());
        StdOut.print(ps.confidenceHi());
        StdOut.print("]\n");
    }

}
