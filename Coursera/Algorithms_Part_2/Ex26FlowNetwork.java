/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

public class Ex26FlowNetwork {
    private static final String NEWLINE = "\n"; // System.getProperty("line.separator");

    private final int V;
    private int E;
    private Bag<Ex25FlowEdge>[] adj;

    public Ex26FlowNetwork(int V) {
        this.V = V;
        this.E = 0;
        initAdj();
    }

    public Ex26FlowNetwork(In in) {
        this.V = in.readInt();
        this.E = in.readInt();
        initAdj();
        while (!in.isEmpty())
            addEdge(new Ex25FlowEdge(in.readInt(), in.readInt(), in.readDouble()));
        this.E /= 2;
    }

    public void addEdge(Ex25FlowEdge e) {
        int v = e.from(), w = e.to();
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public Iterable<Ex25FlowEdge> adj(int v) {
        return adj[v];
    }

    public Iterable<Ex25FlowEdge> edges() {
        return null;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ":  ");
            for (Ex25FlowEdge e : adj[v]) {
                if (e.to() != v) s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    private void initAdj() {
        adj = (Bag<Ex25FlowEdge>[]) new Bag[V()];
        for (int i = 0; i < V(); i++)
            adj[i] = new Bag<>();
    }

    public static void main(String[] args) {

    }
}
