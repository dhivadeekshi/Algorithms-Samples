/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class AS5PointSET {
    private SET<Point2D> points;

    public AS5PointSET() {                              // construct an empty set of points
        points = new SET<>();
    }

    public boolean isEmpty() {                      // is the set empty?
        return points.isEmpty();
    }

    public int size() {                         // number of points in the set
        return points.size();
    }

    public void insert(
            Point2D p) {              // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException();
        if (!contains(p))
            points.add(p);
    }

    public boolean contains(Point2D p) {            // does the set contain point p?
        if (p == null) throw new IllegalArgumentException();
        return points.contains(p);
    }

    public void draw() {                         // draw all points to standard draw
        for (Point2D p : points)
            StdDraw.circle(p.x(), p.y(), 0.01);
    }

    public Iterable<Point2D> range(
            RectHV rect) {             // all points that are inside the rectangle (or on the boundary)
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> queue = new Queue<>();
        for (Point2D p : points) {
            if (rect.contains(p))
                queue.enqueue(p);
        }
        return queue;
    }

    public Point2D nearest(
            Point2D p) {             // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new IllegalArgumentException();
        Point2D neighbour = null;
        double nearest = Double.POSITIVE_INFINITY;
        for (Point2D point : points) {
            double distance = p.distanceSquaredTo(point);
            if (distance < nearest) {
                nearest = distance;
                neighbour = point;
            }
        }
        return neighbour;
    }

    public static void main(
            String[] args)                  // unit testing of the methods (optional)
    {

    }
}
