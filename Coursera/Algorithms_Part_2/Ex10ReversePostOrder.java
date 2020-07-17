/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;

public class Ex10ReversePostOrder {

    private boolean[] marked;
    private Stack<Integer> reverseOrder;

    public Ex10ReversePostOrder(Ex6Digraph G) {
        reverseOrder = new Stack<>();
        marked = new boolean[G.V()];
        for (int i = 0; i < G.V(); i++)
            marked[i] = false;
        for (int i = 0; i < G.V(); i++) {
            if (!marked[i]) dfs(G, i);
        }
    }

    public Iterable<Integer> order() {
        return reverseOrder;
    }

    private void dfs(Ex6Digraph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[v]) {
                dfs(G, w);
            }
        }
        reverseOrder.push(v);
    }

    public static void main(String[] args) {

    }
}
