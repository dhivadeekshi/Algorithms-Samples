/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Ex11StrongComponentsDAG {

    private int[] scc;
    private int count = 0;

    public Ex11StrongComponentsDAG(Ex6Digraph G) {

        boolean[] marked = new boolean[G.V()];
        scc = new int[G.V()];
        for (int i = 0; i < G.V(); i++) {
            scc[i] = -1;
            marked[i] = false;
        }

        Ex10ReversePostOrder reversePostOrder = new Ex10ReversePostOrder(G.reverse());
        for (int v : reversePostOrder.order()) {
            if (!marked[v]) {
                dfs(G, marked, v);
                count++;
            }
        }
    }

    public boolean conneccted(int v, int w) {
        return scc[v] == scc[w];
    }

    public int count() {
        return count;
    }

    private void dfs(Ex6Digraph G, boolean[] marked, int v) {
        marked[v] = true;
        scc[v] = count;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, marked, w);
            }
        }
    }

    public static void main(String[] args) {

    }
}
