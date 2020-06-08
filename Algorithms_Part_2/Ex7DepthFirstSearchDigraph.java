/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;

public class Ex7DepthFirstSearchDigraph {

    private boolean[] marked;
    private int[] edgeTo;

    public Ex7DepthFirstSearchDigraph(Ex6Digraph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        for (int i = 0; i < G.V(); i++) {
            marked[i] = false;
            edgeTo[i] = -1;
        }

        dfs(G, s);
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        Stack<Integer> path = new Stack<>();
        path.push(v);
        for (int w = v; edgeTo[w] != -1; w = edgeTo[w])
            path.push(edgeTo[w]);
        return path;
    }

    private void dfs(Ex6Digraph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
                edgeTo[w] = v;
            }
        }
    }


    public static void main(String[] args) {

    }
}
