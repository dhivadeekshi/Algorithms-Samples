/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Ex12Edge implements Comparable<Ex12Edge> {

    private final int v, w;
    private final double weight;

    public Ex12Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        return vertex == this.v ? this.w : this.v;
    }

    public int compareTo(Ex12Edge that) {
        return Double.compare(this.weight, that.weight);
    }

    public double weight() {
        return weight;
    }

    public String toString() {
        return v + " <-- " + weight + " --> " + w;
    }

    public static void main(String[] args) {

    }
}
