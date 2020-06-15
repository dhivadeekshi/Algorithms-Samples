/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

import java.util.Iterator;

public class Ex20EdgeWeightedDigraph {
    private Bag<Ex19DirectedEdge>[] adj;
    private int V, E;

    public Ex20EdgeWeightedDigraph(int v) {
        initAdj(v);
        this.V = v;
        this.E = 0;
    }

    public Ex20EdgeWeightedDigraph(In in) {
        int v = in.readInt();
        int e = in.readInt();
        initAdj(v);
        this.V = v;
        this.E = 0;
        while (!in.isEmpty()) {
            addEdge(new Ex19DirectedEdge(in.readInt(), in.readInt(), in.readDouble()));
        }
    }

    public void addEdge(Ex19DirectedEdge e) {
        int v = e.from();
        adj[v].add(e);
        this.E++;
    }

    public Iterable<Ex19DirectedEdge> adj(int v) {
        return adj[v];
    }

    public Iterable<Ex19DirectedEdge> edges() {
        return () -> new Iterator<Ex19DirectedEdge>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Ex19DirectedEdge next() {
                return null;
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
        builder.append(V()).append(" vertices ").append(E()).append(" edges").append("\n");
        for (int i = 0; i < V(); i++) {
            builder.append(i).append(": ");
            for (Ex19DirectedEdge e : adj[i])
                builder.append(e).append(" ");
            builder.append("\n");
        }
        return builder.toString();
    }

    private void initAdj(int v) {
        adj = (Bag<Ex19DirectedEdge>[]) new Bag[v];
        for (int i = 0; i < v; i++)
            adj[i] = new Bag<>();
    }

    public static void main(String[] args) {

    }
}
