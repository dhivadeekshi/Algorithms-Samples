/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Ex24BellmanFordSortestPath implements Ex21SortestPath {

    private Ex19DirectedEdge[] edgeTo;
    private double[] distTo;

    public Ex24BellmanFordSortestPath(Ex20EdgeWeightedDigraph G, int s) {
        edgeTo = new Ex19DirectedEdge[G.V()];
        distTo = new double[G.V()];
        for (int i = 0; i < G.V(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }

        distTo[s] = 0.0;
        for (int i = 0; i < G.V(); i++) {
            for (int v = 0; v < G.V(); v++) {
                for (Ex19DirectedEdge e : G.adj(v))
                    relax(e);
            }
        }
    }

    @Override
    public double distTo(int v) {
        return distTo[v];
    }

    @Override
    public Iterable<Ex19DirectedEdge> pathTo(int v) {
        Stack<Ex19DirectedEdge> path = new Stack<>();
        for (Ex19DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);
        return path;
    }

    @Override
    public boolean hasPathTo(int v) {
        return edgeTo[v] != null;
    }

    private void relax(Ex19DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }

    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        In in = new In("10000EWD.txt");

        Ex20EdgeWeightedDigraph G = new Ex20EdgeWeightedDigraph(in);
        Ex24BellmanFordSortestPath sp = new Ex24BellmanFordSortestPath(G, 0);
        StdOut.println(G);

        for (int i = 1; i < G.V(); i++) {
            StdOut.printf("distTo " + i + ": %.2f path: ", sp.distTo(i));
            for (Ex19DirectedEdge e : sp.pathTo(i))
                StdOut.print(e + " ");
            StdOut.println();
        }

        StdOut.println();
        StdOut.println("Time Elapsed: " + stopwatch.elapsedTime());
    }
}
