/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class Ex8BreadthFirstSearchDigraph {

    private boolean[] marked;
    private int[] edgeTo;

    public Ex8BreadthFirstSearchDigraph(Ex6Digraph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        for (int i = 0; i < G.V(); i++) {
            marked[i] = false;
            edgeTo[i] = -1;
        }
        bfs(G, s);
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

    private void bfs(Ex6Digraph G, int s) {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(s);
        marked[s] = true;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    queue.enqueue(w);
                    edgeTo[w] = v;
                    marked[w] = true;
                }
            }
        }
    }

    public static void main(String[] args) {

    }
}
