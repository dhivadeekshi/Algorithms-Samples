/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Ex15KruskalsMST implements Ex14MST {

    private final Bag<Ex12Edge> edges;
    private final double weight;

    public Ex15KruskalsMST(Ex13EdgeWeightedGraph G) {
        edges = new Bag<>();
        MinPQ<Ex12Edge> pq = new MinPQ<>();
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(G.V());
        for (int v = 0; v < G.V(); v++) {
            for (Ex12Edge e : G.adj(v))
                pq.insert(e);
        }
        double totalWeight = 0;
        while (!pq.isEmpty()) {
            Ex12Edge e = pq.delMin();
            int v = e.either(), w = e.other(v);
            if (!uf.connected(v, w)) {
                uf.union(v, w);
                edges.add(e);
                totalWeight += e.weight();
            }
        }
        weight = totalWeight;
    }

    @Override
    public Iterable<Ex12Edge> edges() {
        return edges;
    }

    @Override
    public double weight() {
        return weight;
    }

    public static void main(String[] args) {
        In in = new In("tinyEWG.txt");
        Ex13EdgeWeightedGraph G = new Ex13EdgeWeightedGraph(in);
        StdOut.println(G);
        Ex15KruskalsMST mst = new Ex15KruskalsMST(G);
        StdOut.println("weight:" + mst.weight + " mst: ");
        for (Ex12Edge e : mst.edges())
            StdOut.println(e);
    }
}
