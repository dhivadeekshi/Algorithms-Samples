/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Ex16PrimsLazyMST implements Ex14MST {

    private Bag<Ex12Edge> edges;
    private MinPQ<Ex12Edge> pq;
    private boolean[] marked;
    private double weight;

    public Ex16PrimsLazyMST(Ex13EdgeWeightedGraph G) {
        edges = new Bag<>();
        pq = new MinPQ<>();
        marked = new boolean[G.V()];
        for (int i = 0; i < G.V(); i++)
            marked[i] = false;

        visit(G, 0);
        while (!pq.isEmpty()) {
            Ex12Edge e = pq.delMin();
            int v = e.either(), w = e.other(v);
            if (marked[v] && marked[w]) continue;
            edges.add(e);
            weight += e.weight();
            if (!marked[v]) visit(G, v);
            if (!marked[w]) visit(G, w);
        }
    }

    @Override
    public Iterable<Ex12Edge> edges() {
        return edges;
    }

    @Override
    public double weight() {
        return weight;
    }

    private void visit(Ex13EdgeWeightedGraph G, int vertex) {
        marked[vertex] = true;
        for (Ex12Edge e : G.adj(vertex)) {
            int v = e.either(), w = e.other(v);
            if (!marked[v] || !marked[w])
                pq.insert(e);
        }
    }

    public static void main(String[] args) {
        In in = new In("tinyEWG.txt");
        Ex13EdgeWeightedGraph G = new Ex13EdgeWeightedGraph(in);
        StdOut.println(G);
        Ex16PrimsLazyMST mst = new Ex16PrimsLazyMST(G);
        StdOut.println("weight:" + mst.weight + " mst: ");
        for (Ex12Edge e : mst.edges())
            StdOut.println(e);
    }
}
