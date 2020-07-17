/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

public class Ex6Digraph {

    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    public Ex6Digraph(int v) {
        this.V = v;
        E = 0;
        init(v);
    }

    public Ex6Digraph(In in) {
        V = in.readInt();
        init(V);
        E = in.readInt();
        E = 0;
        while (!in.isEmpty())
            addEdge(in.readInt(), in.readInt());
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        E++;
    }

    public int E() {
        return E;
    }

    public int V() {
        return V;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public Ex6Digraph reverse() {
        Ex6Digraph reverse = new Ex6Digraph(V);
        for (int i = 0; i < V; i++) {
            for (int w : adj(i)) {
                reverse.addEdge(w, i);
            }
        }
        return reverse;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < V; i++)
            for (int w : adj(i))
                builder.append(i).append("->").append(w).append("\n");
        return builder.toString();
    }

    private void init(int v) {
        adj = (Bag<Integer>[]) new Bag[v];
        for (int i = 0; i < v; i++)
            adj[i] = new Bag<>();
    }

    public static void main(String[] args) {

    }
}
