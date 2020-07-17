/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class Ex3BreadthFirstSearchPaths {

    private boolean[] visited;
    private int[] edgeTo;

    private Ex3BreadthFirstSearchPaths(Ex1Graph g, int s) {
        visited = new boolean[g.V()];
        edgeTo = new int[g.V()];
        for (int i = 0; i < g.V(); i++) {
            visited[i] = false;
            edgeTo[i] = -1;
        }

        bfs(g, s);
    }

    public boolean hasPathTo(int v) {
        return visited[v];
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

    private void bfs(Ex1Graph g, int s) {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(s);
        visited[s] = true;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : g.adj(v)) {
                if (!visited[w]) {
                    queue.enqueue(w);
                    visited[w] = true;
                    edgeTo[w] = v;
                }
            }
        }
    }

    public static void main(String[] args) {

    }
}
