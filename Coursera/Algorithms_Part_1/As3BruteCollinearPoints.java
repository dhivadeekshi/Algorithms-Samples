/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class As3BruteCollinearPoints {
    private As3LineSegment[] segments = null;

    public As3BruteCollinearPoints(
            As3Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null || checkForIllegalArguments(points))
            throw new IllegalArgumentException();
        int n = points.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int x = k + 1; x < n; x++) {
                        if (isOnSameLine(points[i], points[j], points[k], points[x])) {
                            int index = numberOfSegments();
                            resize(numberOfSegments() + 1);
                            segments[index] = new As3LineSegment(
                                    min(points[i], points[j], points[k], points[x]),
                                    max(points[i], points[j], points[k], points[x]));
                        }
                    }
                }
            }
        }
    }

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
        return segments == null ? 0 : segments.length;
    }

    public As3LineSegment[] segments()                // the line segments
    {
        As3LineSegment[] copy = new As3LineSegment[numberOfSegments()];
        for (int i = 0; i < numberOfSegments(); i++)
            copy[i] = segments[i];
        return copy;
    }

    private void resize(int capacity) {
        As3LineSegment[] copy = new As3LineSegment[capacity];
        for (int i = 0; i < numberOfSegments(); i++)
            copy[i] = segments[i];
        segments = copy;
    }

    private boolean less(As3Point a, As3Point b) {
        return a.compareTo(b) <= 0;
    }

    private As3Point min(As3Point a, As3Point b, As3Point c, As3Point d) {
        As3Point result = a;
        if (less(b, result)) result = b;
        if (less(c, result)) result = c;
        if (less(d, result)) result = d;
        return result;
    }

    private As3Point max(As3Point a, As3Point b, As3Point c, As3Point d) {
        As3Point result = a;
        if (less(result, b)) result = b;
        if (less(result, c)) result = c;
        if (less(result, d)) result = d;
        return result;
    }

    private boolean isOnSameLine(As3Point a, As3Point b, As3Point c, As3Point d) {
        double slopea = a.slopeTo(b);
        double slopeb = a.slopeTo(c);
        double slopec = a.slopeTo(d);
        return (Double.compare(slopea, slopeb) == 0 && Double.compare(slopeb, slopec) == 0);
    }
}
