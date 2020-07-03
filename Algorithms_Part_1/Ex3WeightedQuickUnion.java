/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Ex3WeightedQuickUnion {
    private int[] id;
    private int[] size;

    public Ex3WeightedQuickUnion(int n) {
        id = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            size[i] = 1;
        }
    }

    private int root(int p) {
        while (p != id[p]) p = id[p];
        return p;
    }

    public void union(int p, int q) {
        int pid = root(p);
        int qid = root(q);
        if (pid == qid) return;
        if (size[pid] >= size[qid]) {
            id[pid] = qid;
            size[pid] += size[qid];
        }
        else {
            id[qid] = pid;
            size[qid] += size[pid];
        }
    }

    public boolean isConnected(int p, int q) {
        return root(p) == root(q);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < id.length; i++)
            result.append(id[i]).append(" ");
        result.append("size:");
        for (int i = 0; i < id.length; i++)
            result.append(size[i]).append(" ");
        return result.toString();
    }

    public static void main(String[] args) {

        int n = StdIn.readInt();
        Ex3WeightedQuickUnion qf = new Ex3WeightedQuickUnion(n);
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
