/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Ex1QuickFind {

    private int[] data;

    public Ex1QuickFind(int n) {
        data = new int[n];
        for (int i = 0; i < n; i++)
            data[i] = i;
    }

    public void union(int p, int q) {
        int pid = data[p];
        int qid = data[q];
        for (int i = 0; i < data.length; i++) {
            if (data[i] == pid)
                data[i] = qid;
        }
    }

    public boolean isConnected(int p, int q) {
        return data[p] == data[q];
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < data.length; i++)
            result.append(data[i]).append(" ");
        return result.toString();
    }

    public static void main(String[] args) {

        int n = StdIn.readInt();
        Ex1QuickFind qf = new Ex1QuickFind(n);
        for (int i = 0; i < n; i++) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();

            if (!qf.isConnected(p, q)) {
                qf.union(p, q);
                StdOut.println("Connected(" + p + "," + q + ") : " + qf.toString());
            }
        }

    }
}
