/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

public class Ex1Graph {

    private final int V;
    private int E;

    private Bag<Integer>[] adj;

    public Ex1Graph(int v) {
        this.V = v;
        E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int i = 0; i < V; i++)
            adj[i] = new Bag<Integer>();
    }

    public Ex1Graph(In in) {
        V = in.readInt();
        int e = in.readInt();
        E = 0;
        adj = (Bag<Integer>[]) new Bag[V];

        while (!in.isEmpty()) {
            addEdge(in.readInt(), in.readInt());
        }
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < V; i++) {
            for (int w : adj(i))
                builder.append(i).append("->").append(w).append("\n");
        }
        return builder.toString();
    }

    public static void main(String[] args) {

    }
}
