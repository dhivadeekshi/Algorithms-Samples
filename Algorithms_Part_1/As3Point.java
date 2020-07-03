/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class As3Point implements Comparable<As3Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public As3Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(As3Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point. Formally, if the two points are
     * (x0, y0) and (x1, y1), then the slope is (y1 - y0) / (x1 - x0). For completeness, the slope
     * is defined to be +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical; and Double.NEGATIVE_INFINITY if
     * (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(As3Point that) {
        if (this.compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        else if (this.y == that.y) return 0.0;
        else if (this.x == that.x) return Double.POSITIVE_INFINITY;
        else {
            return (double) (that.y - this.y) / (double) (that.x - this.x);
        }
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate. Formally, the invoking
     * point (x0, y0) is less than the argument point (x1, y1) if and only if either y0 < y1 or if
     * y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument point (x0 = x1 and y0 =
     * y1); a negative integer if this point is less than the argument point; and a positive integer
     * if this point is greater than the argument point
     */
    public int compareTo(As3Point that) {
        if (this.x == that.x && this.y == that.y) return 0;
        else if (this.y < that.y || (this.y == that.y && this.x < that.x)) return -1;
        else return 1;
    }

    /**
     * Compares two points by the slope they make with this point. The slope is defined as in the
     * slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<As3Point> slopeOrder() {
        return new PointSlopeComparator();
    }

    private class PointSlopeComparator implements Comparator<As3Point> {
        @Override
        public int compare(As3Point o1, As3Point o2) {
            double slope1 = slopeTo(o1);
            double slope2 = slopeTo(o2);
            return Double.compare(slope1, slope2);
        }
    }

    /**
     * Returns a string representation of this point. This method is provide for debugging; your
     * program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        As3Point point = new As3Point(10, 15);
        As3Point point1 = new As3Point(15, 10);
        StdOut.println(point.toString());
        StdOut.println("compareTo " + point1.toString() + " :" + point.compareTo(point1));
        StdOut.println("slopeTo " + point1.toString() + " :" + point.slopeTo(point1));

        As3Point[] points = new As3Point[5];
        for (int i = 0; i < points.length; i++) {
            points[i] = new As3Point(i, i);
            StdOut.print(points[i].toString() + ",");
        }
        StdOut.println();
        for (int i = 1; i < points.length; i++) {
            As3Point[] cPoints = Arrays.copyOfRange(points, i, points.length);
            for (int j = 0; j < cPoints.length; j++)
                StdOut.print(cPoints[j].toString() + ",");
            StdOut.println();
        }
    }
}
