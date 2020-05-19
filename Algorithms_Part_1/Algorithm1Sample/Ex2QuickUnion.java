/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Ex2QuickUnion {

    private int[] id;

    public Ex2QuickUnion(int n) {
        id = new int[n];
        for (int i = 0; i < n; i++)
            id[i] = i;
    }

    private int root(int p) {
        while (id[p] != p)
            p = id[p];
        return p;
    }

    public void union(int p, int q) {
        int pid = root(p);
        int qid = root(q);
        id[pid] = qid;
    }

    public boolean isConnected(int p, int q) {
        return root(p) == root(q);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < id.length; i++)
            result.append(id[i]).append(" ");
        return result.toString();
    }

    public static void main(String[] args) {

        int n = StdIn.readInt();
        Ex2QuickUnion qf = new Ex2QuickUnion(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();

            if (!qf.isConnected(p, q)) {
                qf.union(p, q);
                StdOut.println("Connected(" + p + "," + q + ") : " + qf.toString());
            }
        }

    }
}
