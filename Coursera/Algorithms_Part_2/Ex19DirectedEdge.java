/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Ex19DirectedEdge implements Comparable<Ex19DirectedEdge> {

    private final int v, w;
    private final double weight;

    public Ex19DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public double weight() {
        return weight;
    }

    public String toString() {
        return v + " -- " + weight + " --> " + w;
    }

    public int compareTo(Ex19DirectedEdge that) {
        return Double.compare(this.weight(), that.weight());
    }

    public static void main(String[] args) {

    }
}
