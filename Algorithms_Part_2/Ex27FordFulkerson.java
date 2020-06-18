/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Ex27FordFulkerson {

    private Ex25FlowEdge[] edgeTo;
    private boolean[] marked;
    private double value;

    public Ex27FordFulkerson(Ex26FlowNetwork G, int s, int t) {
        value = 0.0;
        while (hasAugumentingPath(G, s, t)) {
            double bottle = Double.POSITIVE_INFINITY;
            for (int v = t; v != s; v = edgeTo[v].other(v))
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));

            for (int v = t; v != s; v = edgeTo[v].other(v))
                edgeTo[v].addResidualFlowTo(v, bottle);

            value += bottle;
        }
    }

    public double value() {
        return value;
    }

    public boolean inCut(int v) {
        return marked[v];
    }

    public void printMinCut() {
        Bag<Integer> fromS = new Bag<>();
        Bag<Integer> toT = new Bag<>();
        for (int i = 0; i < marked.length; i++) {
            if (marked[i]) fromS.add(i);
            else toT.add(i);
        }

        StdOut.print("S: ");
        for (int i : fromS)
            StdOut.print(i + " ");
        StdOut.println();
        StdOut.print("T: ");
        for (int i : toT)
            StdOut.print(i + " ");
        StdOut.println();
    }

    private boolean hasAugumentingPath(Ex26FlowNetwork G, int s, int t) {
        edgeTo = new Ex25FlowEdge[G.V()];
        marked = new boolean[G.V()];

        Queue<Integer> queue = new Queue<>();
        queue.enqueue(s);
        marked[s] = true;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (Ex25FlowEdge e : G.adj(v)) {
                int w = e.other(v);
                if (e.residualCapacityTo(w) > 0 && !marked[w]) {
                    edgeTo[w] = e;
                    queue.enqueue(w);
                    marked[w] = true;
                }
            }
        }

        return marked[t];
    }

    private static void printMinCut(FordFulkerson ff, int V) {
        Bag<Integer> fromS = new Bag<>();
        Bag<Integer> toT = new Bag<>();
        for (int i = 0; i < V; i++) {
            if (ff.inCut(i)) fromS.add(i);
            else toT.add(i);
        }

        StdOut.print("S: ");
        for (int i : fromS)
            StdOut.print(i + " ");
        StdOut.println();
        StdOut.print("T: ");
        for (int i : toT)
            StdOut.print(i + " ");
        StdOut.println();
    }

    public static void main(String[] args) {
        In in = new In("testFN.txt");
        // if (args.length > 0) in = new In(args[0]);
        Ex26FlowNetwork fn = new Ex26FlowNetwork(in);
        StdOut.println(fn);
        Ex27FordFulkerson ff = new Ex27FordFulkerson(fn, 0, fn.V() - 1);
        StdOut.println("value:" + ff.value());
        ff.printMinCut();
        StdOut.println(fn);
    }
}
