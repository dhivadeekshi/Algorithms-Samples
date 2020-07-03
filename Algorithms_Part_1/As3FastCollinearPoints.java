/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class As3FastCollinearPoints {

    private class LineSegmentData {
        private final As3Point[] points;

        public LineSegmentData(As3Point[] points) {
            this.points = Arrays.copyOf(points, points.length);
            Merge.sort(this.points);
        }

        public As3LineSegment lineSegment() {
            return new As3LineSegment(points[0], points[points.length - 1]);
        }

        public int size() {
            return points == null ? 0 : points.length;
        }

        public As3Point pointAt(int index) {
            if (index < 0 || index >= points.length)
                throw new IllegalArgumentException("index out of range");
            return points[index];
        }

        public boolean isEqual(LineSegmentData that) {
            if (size() != that.size()) return false;
            for (int i = 0; i < size(); i++)
                if (points[i].compareTo(that.pointAt(i)) != 0)
                    return false;
            return true;
        }

        public boolean isSubSegment(LineSegmentData that) {
            int j = 0;
            for (int i = 0; i < size() && j < that.size(); i++) {
                if (points[i].compareTo(that.pointAt(j)) == 0) {
                    j++;
                }
            }
            return j == that.size();
        }
    }

    private LineSegmentData[] segmentData;
    // private LineSegment[] segments = null;

    public As3FastCollinearPoints(
            As3Point[] points)     // finds all line segments containing 4 or more points
    {
        if (points == null || checkForIllegalArguments(points))
            throw new IllegalArgumentException();
        As3Point[] myPoints = Arrays.copyOf(points, points.length);
        int n = myPoints.length;
        if (n < 4) return;
        As3Point[] cPoints = Arrays.copyOf(myPoints, myPoints.length);
        for (int i = 0; i < n; i++) {

            // Point[] cPoints = Arrays.copyOfRange(points, i + 1, points.length);
            Arrays.sort(cPoints, myPoints[i].slopeOrder());

            int start = 1;
            double prevSlope = myPoints[i].slopeTo(cPoints[1]);
            for (int j = 1; j < cPoints.length; j++) {
                double slope = myPoints[i].slopeTo(cPoints[j]);
                // StdOut.print("slope(" + i + "," + j + ")");
                // StdOut.print("[" + points[i].toString() + "," + cPoints[j].toString() + "] :");
                // StdOut.print(slope);
                // StdOut.print(" start:" + start);
                boolean isSlopeChanged = Double.compare(prevSlope, slope) != 0;
                boolean isLastReached = j == cPoints.length - 1;
                // StdOut.print(" isSlopeChanged?" + isSlopeChanged);
                // StdOut.print(" isLastReached?" + isLastReached);
                if (isSlopeChanged || isLastReached) {
                    int end = isSlopeChanged ? j - 1 : j;
                    // StdOut.print(" end:" + end);
                    // StdOut.print(" count:" + (end - start));
                    // StdOut.println();
                    if (end - start >= 2)
                        addToSegment(cPoints, myPoints[i], start, end);
                    prevSlope = slope;
                    start = j;
                }
                // else
                //    StdOut.println();
            }
        }
    }

    private void addToSegment(As3Point[] points, As3Point base, int start, int end) {
        As3Point[] segmentPoints = new As3Point[end - start + 2];
        for (int i = 0; i <= end - start; i++)
            segmentPoints[i] = points[start + i];
        segmentPoints[segmentPoints.length - 1] = base;

        int n = numberOfSegments();
        LineSegmentData segment = new LineSegmentData(segmentPoints);
        for (int i = 0; i < n; i++) {
            if (segmentData[i].isSubSegment(segment)) {
                return;
            }
        }

        if (segmentData == null)
            segmentData = new LineSegmentData[1];
        else
            segmentData = Arrays.copyOf(segmentData, n + 1);
        segmentData[n] = segment;

        /* Point min = base;
        Point max = base;
        // StdOut.print("addToSegment : " + base.toString());
        for (int i = start; i <= end; i++) {

            // StdOut.print(" -> " + points[i].toString());
            if (less(points[i], min)) {
                min = points[i];
            }
            else if (less(max, points[i])) {
                max = points[i];
            }
        }
        // StdOut.println();
        addSegment(min, max); */
    }

    /* private void addSegment(Point a, Point b) {
        // StdOut.println("addSegment : " + a.toString() + " -> " + b.toString());
        LineSegment segment = new LineSegment(a, b);
        for (int i = 0; i < numberOfSegments(); i++) {
            if (segment.toString().equals(segments[i].toString()))
                return;
        }
        int index = numberOfSegments();
        resize(numberOfSegments() + 1);
        segments[index] = segment;
    } */

    private boolean checkForIllegalArguments(As3Point[] points) {
        int n = points.length;
        for (int i = 0; i < n; i++) {
            if (points[i] == null) return true;
            for (int j = i + 1; j < n; j++) {
                if (points[j] == null) return true;
                if (points[i].compareTo(points[j]) == 0) return true;
            }
        }
        return false;
    }

    public int numberOfSegments()        // the number of line segments
    {
        return segmentData == null ? 0 : segmentData.length;
        // return segments == null ? 0 : segments.length;
    }

    public As3LineSegment[] segments()                // the line segments
    {
        As3LineSegment[] copy = new As3LineSegment[numberOfSegments()];
        for (int i = 0; i < numberOfSegments(); i++)
            copy[i] = segmentData[i].lineSegment();
        // copy[i] = segments[i];
        return copy;
    }

    /* private boolean less(Point a, Point b) {
        return a.compareTo(b) <= 0;
    }*/

    /* private void resize(int capacity) {
        LineSegment[] copy = new LineSegment[capacity];
        for (int i = 0; i < numberOfSegments(); i++)
            copy[i] = segments[i];
        segments = copy;
    } */

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        As3Point[] points = new As3Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new As3Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (As3Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        As3FastCollinearPoints collinear = new As3FastCollinearPoints(points);
        for (As3LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();


        // Unit Test
        collinear = new As3FastCollinearPoints(points);
        for (int i = 1; i <= 10; i++)
            for (As3LineSegment segment : collinear.segments())
                StdOut.println("Test " + i + " : " + segment);
    }
}
