/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Ex17PrimsEagerMST implements Ex14MST {

    private Bag<Ex12Edge> edges;
    private double weight;

    private Ex18IndexMinPQ<Ex12Edge> pq;
    private boolean[] marked;

    public Ex17PrimsEagerMST(Ex13EdgeWeightedGraph G) {
        edges = new Bag<>();
        weight = 0;

        pq = new Ex18IndexMinPQ<>(G.V());
        marked = new boolean[G.V()];
        for (int i = 0; i < G.V(); i++) marked[i] = false;

        visit(G, 0);
        while (!pq.isEmpty()) {
            Ex12Edge e = pq.minKey();
            StdOut.println("minKey:" + e + "  deleteMin:" + pq.delMin() + " size:" + pq.size());
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
            if (marked[v] && marked[w]) continue;
            int newV = marked[v] ? w : v;
            if (pq.contains(newV)) {
                if (Double.compare(e.weight(), pq.keyOf(newV).weight()) < 0)
                    pq.changeKey(newV, e);
            }
            else
                pq.insert(newV, e);
        }
    }

    public static void main(String[] args) {
        In in = new In("tinyEWG.txt");
        Ex13EdgeWeightedGraph G = new Ex13EdgeWeightedGraph(in);
        StdOut.println(G);
        Ex17PrimsEagerMST mst = new Ex17PrimsEagerMST(G);
        StdOut.println();
        StdOut.println("weight:" + mst.weight + " mst: ");
        for (Ex12Edge e : mst.edges())
            StdOut.println(e);
    }
}
