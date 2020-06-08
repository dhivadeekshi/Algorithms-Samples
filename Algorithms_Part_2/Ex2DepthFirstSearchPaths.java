/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;

public class Ex2DepthFirstSearchPaths {
    private boolean[] visited;
    private int[] edgeTo;

    public Ex2DepthFirstSearchPaths(Ex1Graph g, int s) {
        visited = new boolean[g.V()];
        edgeTo = new int[g.V()];
        for (int i = 0; i < g.V(); i++) {
            visited[i] = false;
            edgeTo[i] = -1;
        }

        dfs(g, s);
    }

    public boolean hasPathTo(int v) {
        return edgeTo[v] >= 0;
    }

    public Iterable<Integer> pathTo(int v) {
        Stack<Integer> path = new Stack<>();
        int w = v;
        path.push(w);
        while (edgeTo[w] != -1) {
            w = edgeTo[w];
            path.push(w);
        }
        return path;
    }

    private void dfs(Ex1Graph g, int s) {
        visited[s] = true;
        for (int v : g.adj(s)) {
            if (!visited[v]) {
                edgeTo[v] = s;
                dfs(g, v);
            }
        }
    }

    public static void main(String[] args) {

    }
}
