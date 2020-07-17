/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public interface Ex21SortestPath {
    // constr
    double distTo(int v);

    Iterable<Ex19DirectedEdge> pathTo(int v);

    boolean hasPathTo(int v);
}
