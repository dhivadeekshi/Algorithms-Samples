/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

public class Ex4ConnectedComponents {

    private boolean[] visited;
    private int[] connectedTo;
    private int count = 0;

    public Ex4ConnectedComponents(Ex1Graph g) {
        visited = new boolean[g.V()];
        connectedTo = new int[g.V()];
        for (int i = 0; i < g.V(); i++) {
            visited[i] = false;
            connectedTo[i] = -1;
        }

        for (int i = 0; i < g.V(); i++) {
            if (!visited[i]) {
                bfs(g, i);
                count++;
            }
        }
    }

    public boolean connected(int v, int w) {
        return connectedTo[v] == connectedTo[w];
    }

    public Iterable<Integer> connectedTo(int v) {
        Queue<Integer> queue = new Queue<>();
        for (int i = 0; i < connectedTo.length; i++)
            if (i != v && connectedTo[i] == connectedTo[v])
                queue.enqueue(i);
        return queue;
    }

    public int count() {
        return count;
    }

    public int id(int v) {
        return connectedTo[v];
    }

    private void bfs(Ex1Graph g, int v) {
        if (visited[v]) return;
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(v);
        visited[v] = true;
        connectedTo[v] = count;
        while (!queue.isEmpty()) {
            int w = queue.dequeue();
            for (int vw : g.adj(w)) {
                if (!visited[vw]) {
                    queue.enqueue(vw);
                    visited[vw] = true;
                    connectedTo[vw] = count;
                }
            }
        }
    }

    public static void main(String[] args) {

    }
}
