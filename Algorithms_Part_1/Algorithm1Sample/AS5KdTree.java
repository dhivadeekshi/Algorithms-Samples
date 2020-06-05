/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;

public class AS5KdTree {
    private static final double POINT_DRAW_SIZE = 0.01;
    private static final boolean VERTICAL_NODE = true;
    private static final RectHV FULL_RECT = new RectHV(0.0, 0.0, 1.0, 1.0);
    private static final boolean DEBUG = false;

    private class Node {
        private Point2D point;
        private Node left;
        private Node right;
        private int count;

        public Node(Point2D p) {
            this.point = p;
            this.count = 1;
        }
    }

    private Node root = null;

    public AS5KdTree() {                               // construct an empty set of points
    }

    public boolean isEmpty() {                      // is the set empty?
        return root == null;
    }

    public int size() {                         // number of points in the set
        return size(root);
    }

    public void insert(
            Point2D p) {              // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException();
        root = put(root, p, VERTICAL_NODE);
    }

    public boolean contains(Point2D p) {            // does the set contain point p?
        if (p == null) throw new IllegalArgumentException();
        return contains(root, p, VERTICAL_NODE);
    }

    public void draw() {                         // draw all points to standard draw
        draw(root, FULL_RECT, VERTICAL_NODE);
    }

    public Iterable<Point2D> range(
            RectHV rect) {             // all points that are inside the rectangle (or on the boundary)
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> queue = new Queue<>();
        range(root, rect, queue, VERTICAL_NODE);
        return queue;
    }

    public Point2D nearest(
            Point2D p) {             // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new IllegalArgumentException();
        return nearest(root, p, FULL_RECT, VERTICAL_NODE);
    }

    private Node put(Node node, Point2D p, boolean isVertical) {
        if (node == null) return new Node(p);
        int cmp = isVertical ? Double.compare(node.point.x(), p.x()) :
                  Double.compare(node.point.y(), p.y());
        if (cmp > 0) node.left = put(node.left, p, !isVertical);
        else if (cmp < 0) node.right = put(node.right, p, !isVertical);
        else if ((!isVertical ? Double.compare(node.point.x(), p.x()) :
                  Double.compare(node.point.y(), p.y())) != 0)
            node.left = put(node.left, p, !isVertical);
        updateCount(node);
        return node;
    }

    private boolean contains(Node node, Point2D p, boolean isVertical) {
        if (node == null) return false;
        int cmp = isVertical ? Double.compare(node.point.x(), p.x()) :
                  Double.compare(node.point.y(), p.y());
        if (cmp > 0) return contains(node.left, p, !isVertical);
        else if (cmp < 0) return contains(node.right, p, !isVertical);
        else if ((!isVertical ? Double.compare(node.point.x(), p.x()) :
                  Double.compare(node.point.y(), p.y())) != 0)
            return contains(node.left, p, !isVertical);
        return true;
    }

    private void range(Node node, RectHV rect, Queue<Point2D> queue, boolean isVertical) {
        if (node == null) return;
        int cmpMin = isVertical ? Double.compare(node.point.x(), rect.xmin()) :
                     Double.compare(node.point.y(), rect.ymin());
        int cmpMax = isVertical ? Double.compare(node.point.x(), rect.xmax()) :
                     Double.compare(node.point.y(), rect.ymax());
        if (cmpMax < 0) range(node.right, rect, queue, !isVertical);
        if (cmpMin > 0) range(node.left, rect, queue, !isVertical);
        if (rect.contains(node.point)) queue.enqueue(node.point);
    }

    private RectHV loRect(Node node, RectHV rect, boolean isVertical) {
        return isVertical ?
               new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax()) :
               new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());
    }

    private RectHV hiRect(Node node, RectHV rect, boolean isVertical) {
        return isVertical ?
               new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax()) :
               new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private Point2D nearest(Node node, Point2D p, RectHV rect, boolean isVertical) {
        if (node == null) return null;

        RectHV loRect = loRect(node, rect, isVertical);
        RectHV hiRect = hiRect(node, rect, isVertical);

        Point2D point = node.point;
        double distance = p.distanceSquaredTo(node.point);

        int cmp = isVertical ? Double.compare(p.x(), node.point.x()) :
                  Double.compare(p.y(), node.point.y());

        Point2D subPoint = null;
        if (cmp <= 0) subPoint = nearest(node.left, p, loRect, !isVertical);
        if (cmp > 0) subPoint = nearest(node.right, p, hiRect, !isVertical);
        if (subPoint != null && subPoint.distanceSquaredTo(p) < distance) {
            point = subPoint;
            distance = p.distanceSquaredTo(point);
            subPoint = null;
        }

        Point2D sortestPoint;
        if (isVertical)
            sortestPoint = new Point2D(node.point.x(), clamp(p.y(), rect.ymin(), rect.ymax()));
        else sortestPoint = new Point2D(clamp(p.x(), rect.xmin(), rect.xmax()), node.point.y());

        if (p.distanceSquaredTo(sortestPoint) < distance) {
            if (cmp <= 0) subPoint = nearest(node.right, p, hiRect, !isVertical);
            if (cmp > 0) subPoint = nearest(node.left, p, loRect, !isVertical);
            if (subPoint != null && subPoint.distanceSquaredTo(p) < distance) {
                point = subPoint;
            }
        }

        return point;
    }

    private void draw(Node node, RectHV rect, boolean isVertical) {
        if (node == null) return;
        draw(node.left, loRect(node, rect, isVertical), !isVertical);
        if (isVertical) {
            StdDraw.setPenColor(Color.RED);
            StdDraw.line(node.point.x(), rect.ymin(),
                         node.point.x(), rect.ymax());
        }
        else
            StdDraw.line(rect.xmin(), node.point.y(),
                         rect.xmax(), node.point.y());
        StdDraw.filledCircle(node.point.x(), node.point.y(), POINT_DRAW_SIZE);
        StdDraw.setPenColor(Color.BLACK);
        draw(node.right, hiRect(node, rect, isVertical), !isVertical);
    }

    private int size(Node node) {
        if (node == null) return 0;
        return node.count;
    }

    private void updateCount(Node node) {
        if (node == null) return;
        node.count = size(node.left) + size(node.right) + 1;
    }

    public static void main(
            String[] args) {                  // unit testing of the methods (optional)

        AS5KdTree kd = new AS5KdTree();
        kd.insert(new Point2D(0.125, 1.0)); // A 1
        kd.insert(new Point2D(0.375, 0.75)); // B 2
        kd.insert(new Point2D(0.75, 0.25)); // C 3
        kd.insert(new Point2D(0.0, 0.75)); // D 4
        kd.insert(new Point2D(0.25, 0.75)); // E 5
        kd.insert(new Point2D(1.0, 0.75)); // F 6
        kd.insert(new Point2D(0.75, 0.5)); // G 7
        kd.insert(new Point2D(0.375, 1.0)); // H 8
        kd.insert(new Point2D(1.0, 0.625)); // I 9
        kd.insert(new Point2D(0.25, 0.25)); // J 10
        kd.insert(new Point2D(0.125, 0.625)); // K 11
        kd.insert(new Point2D(0.25, 0.625)); // L 12
        kd.insert(new Point2D(0.375, 0.125)); // M 13
        kd.insert(new Point2D(0.125, 0.125)); // N 14
        kd.insert(new Point2D(0.75, 1.0)); // O 15
        kd.insert(new Point2D(1.0, 0.25)); // P 16
        kd.insert(new Point2D(0.75, 0.375)); // Q 17
        kd.insert(new Point2D(0.75, 0.625)); // R 18
        kd.insert(new Point2D(0.875, 0.0)); // S 19
        kd.insert(new Point2D(0.75, 0.0)); // T 20


        StdOut.println("size:" + kd.size());
        for (Point2D point : kd.range(FULL_RECT))
            StdOut.println("contains " + point.toString() + "? " + kd.contains(point));
        RectHV rect = new RectHV(0.0, 0.125, 0.5, 0.875);
        StdDraw.setPenColor(Color.GREEN);
        double halfWidth = (rect.xmax() - rect.xmin()) / 2.0;
        double halfHeight = (rect.ymax() - rect.ymin()) / 2.0;
        StdDraw.filledRectangle(rect.xmin() + halfWidth, rect.ymin() + halfHeight, halfWidth,
                                halfHeight);

        StdOut.println("range:");
        for (Point2D point : kd.range(rect))
            StdOut.println(" " + point.toString());
        StdOut.println();


        findNearest(new Point2D(0.135, 0.76), kd);
        findNearest(new Point2D(0.5, 0.5), kd);
        findNearest(new Point2D(0.25, 0.367), kd);


        StdDraw.setPenColor(Color.BLACK);
        kd.draw();

        testContains();
    }

    private static void testContains() {
        AS5KdTree kd = new AS5KdTree();
        kd.insert(new Point2D(0.25, 0.75));
        kd.insert(new Point2D(1.0, 0.5));
        kd.insert(new Point2D(0.5, 0.5));
        kd.insert(new Point2D(0.75, 0.5));
        kd.insert(new Point2D(0.75, 0.75));
        kd.insert(new Point2D(1.0, 0.25));
        kd.insert(new Point2D(0.25, 1.0));
        kd.insert(new Point2D(0.75, 1.0));
        kd.insert(new Point2D(0.25, 0.0));
        kd.insert(new Point2D(0.5, 0.75));

        Point2D point = new Point2D(0.25, 1.0);
        StdOut.println("Test Contins => contains " + point.toString() + "? " + kd.contains(point));
    }

    private static void findNearest(Point2D queryNearest, AS5KdTree kd) {
        StdDraw.setPenColor(Color.ORANGE);
        StdDraw.filledCircle(queryNearest.x(), queryNearest.y(), POINT_DRAW_SIZE);

        Point2D nearest = kd.nearest(queryNearest);
        StdOut.println("nearest of " + queryNearest.toString() + " is " + nearest.toString());
        StdDraw.filledCircle(nearest.x(), nearest.y(), POINT_DRAW_SIZE * 2);

        StdDraw.line(queryNearest.x(), queryNearest.y(),
                     nearest.x(), nearest.y());
    }
}
