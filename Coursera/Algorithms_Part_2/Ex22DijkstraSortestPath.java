/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Ex22DijkstraSortestPath implements Ex21SortestPath {

    private Ex19DirectedEdge[] edgeTo;
    private double[] distTo;

    public Ex22DijkstraSortestPath(Ex20EdgeWeightedDigraph G, int s) {
        edgeTo = new Ex19DirectedEdge[G.V()];
        distTo = new double[G.V()];
        boolean[] marked = new boolean[G.V()];
        for (int i = 0; i < G.V(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
            marked[i] = false;
        }
        Ex18IndexMinPQ<Double> minPQ = new Ex18IndexMinPQ<>(G.V());
        distTo[0] = 0.0;
        minPQ.insert(s, 0.0);
        while (!minPQ.isEmpty()) {
            int vertex = minPQ.delMin();
            marked[vertex] = true;
            for (Ex19DirectedEdge e : G.adj(vertex)) {
                int w = e.to();
                if (!marked[w]) {
                    relax(e);
                    if (!minPQ.contains(w))
                        minPQ.insert(w, distTo[w]);
                    else
                        minPQ.decreaseKey(w, distTo(w));
                }
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
        for (Ex19DirectedEdge edge = edgeTo[v]; edge != null; edge = edgeTo[edge.from()])
            path.push(edge);
        return path;
    }

    @Override
    public boolean hasPathTo(int v) {
        return edgeTo[v] != null;
    }

    private void relax(Ex19DirectedEdge edge) {
        int v = edge.from(), w = edge.to();
        if (distTo[w] > distTo[v] + edge.weight()) {
            distTo[w] = distTo[v] + edge.weight();
            edgeTo[w] = edge;
        }
    }

    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        In in = new In("10000EWD.txt");
        Ex20EdgeWeightedDigraph G = new Ex20EdgeWeightedDigraph(in);
        Ex22DijkstraSortestPath sp = new Ex22DijkstraSortestPath(G, 0);
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
