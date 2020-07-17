/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Ex23TopologicalSortestPath implements Ex21SortestPath {

    private Ex19DirectedEdge[] edgeTo;
    private double[] distTo;

    public Ex23TopologicalSortestPath(Ex20EdgeWeightedDigraph G, int s) {
        edgeTo = new Ex19DirectedEdge[G.V()];
        distTo = new double[G.V()];
        boolean[] marked = new boolean[G.V()];
        for (int i = 0; i < G.V(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
            marked[i] = false;
        }

        distTo[s] = 0.0;
        Ex22TopologicalEWG topological = new Ex22TopologicalEWG(G);
        for (int i : topological.order()) {
            marked[i] = true;
            for (Ex19DirectedEdge e : G.adj(i)) {
                if (!marked[e.to()]) relax(e);
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
        In in = new In("1000EWD.txt");
        Ex20EdgeWeightedDigraph G = new Ex20EdgeWeightedDigraph(in);
        Ex23TopologicalSortestPath sp = new Ex23TopologicalSortestPath(G, 0);
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
