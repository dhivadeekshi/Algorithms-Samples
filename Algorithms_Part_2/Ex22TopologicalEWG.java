/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;

public class Ex22TopologicalEWG {

    private Stack<Integer> order;
    private boolean[] marked;

    public Ex22TopologicalEWG(Ex20EdgeWeightedDigraph G) {
        order = new Stack<>();
        marked = new boolean[G.V()];
        for (int i = 0; i < G.V(); i++)
            marked[i] = false;

        for (int i = 0; i < G.V(); i++) {
            if (!marked[i]) visit(G, i);
        }
    }

    public Iterable<Integer> order() {
        return order;
    }

    private void visit(Ex20EdgeWeightedDigraph G, int v) {
        marked[v] = true;
        for (Ex19DirectedEdge e : G.adj(v)) {
            if (!marked[e.to()])
                visit(G, e.to());
        }
        order.push(v);
    }

    public static void main(String[] args) {

    }
}
