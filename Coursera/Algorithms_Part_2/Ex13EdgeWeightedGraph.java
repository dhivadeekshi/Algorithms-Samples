/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Ex13EdgeWeightedGraph {

    private final int V;
    private int E;
    private Bag<Ex12Edge>[] adj;

    public Ex13EdgeWeightedGraph(int V) {
        this.V = V;
        this.E = 0;
        this.adj = (Bag<Ex12Edge>[]) new Bag[V];
        for (int i = 0; i < V; i++)
            adj[i] = new Bag<>();
    }

    public Ex13EdgeWeightedGraph(In in) {
        this.V = in.readInt();
        this.E = in.readInt();
        this.adj = (Bag<Ex12Edge>[]) new Bag[V];
        for (int i = 0; i < V; i++)
            adj[i] = new Bag<>();

        while (!in.isEmpty()) {
            addEdge(new Ex12Edge(in.readInt(), in.readInt(), in.readDouble()));
            this.E--;
        }
    }

    public void addEdge(Ex12Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        this.E++;
    }

    public Iterable<Ex12Edge> adj(int v) {
        return adj[v];
    }

    public Iterable<Ex12Edge> edges() {
        return () -> new Iterator<Ex12Edge>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < V();
            }

            @Override
            public Ex12Edge next() {
                if (!hasNext()) throw new NoSuchElementException();
                if (!adj[current].iterator().hasNext()) current++;
                return adj[current].iterator().next();
            }
        };
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(V).append(" vertices ");
        builder.append(E).append(" edges");
        builder.append("\n");
        for (int i = 0; i < V(); i++) {
            builder.append(i).append(":");
            for (Ex12Edge e : adj(i))
                builder.append(e).append(" ");
            builder.append("\n");
        }
        /* for (Ex12Edge edge : edges())
            builder.append(edge.toString()).append("\n");*/
        return builder.toString();
    }

    public static void main(String[] args) {
        In in = new In("tinyEWG.txt");
        Ex13EdgeWeightedGraph graph = new Ex13EdgeWeightedGraph(in);
        StdOut.println(graph.toString());
    }
}
