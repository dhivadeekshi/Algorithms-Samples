/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;

public class As2SeamCarver {
    private static final double MAX_ENERGY = 1000.0;

    private final Picture picture;
    private int width;
    private int height;

    private class Node {
        private int fromTop;
        private int fromLeft;
        private double distFromTop;
        private double distFromLeft;

        public Node() {
            this.distFromLeft = Double.POSITIVE_INFINITY;
            this.distFromTop = Double.POSITIVE_INFINITY;
            this.fromLeft = -1;
            this.fromTop = -1;
        }

        public String toString() {
            return String.format("[L:%9.2f,%d T:%9.2f,%d]",
                                 distFromLeft, fromLeft,
                                 distFromTop, fromTop);
        }
    }

    private Node[][] seams;
    private int currentVerticalSeamIndex;
    private int currentHorizontalSeamIndex;

    // create a seam carver object based on the given picture
    public As2SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException("Picture is null");
        this.picture = new Picture(picture);
        this.width = picture.width();
        this.height = picture.height();

        this.seams = new Node[width()][height()];
        fetchAllSeams();

        this.currentHorizontalSeamIndex = -1;
        this.currentVerticalSeamIndex = -1;
    }

    // current picture
    public Picture picture() {
        Picture pic = new Picture(width(), height());
        for (int row = 0; row < height(); row++)
            for (int col = 0; col < width(); col++)
                pic.set(col, row, this.picture.get(col, row));
        return pic;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validateX(x);
        validateY(y);

        if (x == 0 || x == width() - 1) return MAX_ENERGY;
        if (y == 0 || y == height() - 1) return MAX_ENERGY;

        Color up = picture.get(x, y - 1);
        Color down = picture.get(x, y + 1);
        Color left = picture.get(x - 1, y);
        Color right = picture.get(x + 1, y);

        double delX = getDelta(right, left);
        double delY = getDelta(down, up);

        return Math.sqrt(delX + delY);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {

        if (currentHorizontalSeamIndex == -1) {
            double min = Double.POSITIVE_INFINITY;
            for (int y = 0; y < height(); y++) {
                double energy = seams[width() - 1][y].distFromLeft;
                if (less(energy, min)/* ||
                        (y == seams[width() - 1][y].fromLeft && equal(energy, min))*/) {
                    currentHorizontalSeamIndex = y;
                    min = energy;
                }
            }
        }

        int index = currentHorizontalSeamIndex;
        int[] seam = new int[width()];
        seam[width() - 1] = index;
        for (int x = width() - 1; x > 0; x--) {
            index = seams[x][index].fromLeft;
            seam[x - 1] = index;
        }
        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

        if (currentVerticalSeamIndex == -1) {
            double min = Double.POSITIVE_INFINITY;
            for (int x = 0; x < width(); x++) {
                double energy = seams[x][height() - 1].distFromTop;
                if (less(energy, min)/* ||
                        (x == seams[x][height() - 1].fromTop && equal(energy, min))*/) {
                    currentVerticalSeamIndex = x;
                    min = energy;
                }
            }
        }

        int index = currentVerticalSeamIndex;
        int[] seam = new int[height()];
        seam[height() - 1] = index;
        for (int y = height() - 1; y > 0; y--) {
            index = seams[index][y].fromTop;
            seam[y - 1] = index;
        }
        return seam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("seam is null");
        if (seam.length != width()) throw new IllegalArgumentException("seam is not valid");
        if (height() <= 1) throw new IllegalArgumentException("not sufficient height");
        int prevY = seam[0];
        for (int col = 0; col < width(); col++) {
            int y = seam[col];
            validateY(y);
            if (Math.abs(prevY - y) > 1)
                throw new IllegalArgumentException("index in seam is invalid");
            prevY = y;
            for (int row = y + 1; row < height(); row++)
                picture.set(col, row - 1, picture.get(col, row));
            picture.set(col, height() - 1, Color.white);
        }
        height--;

        fetchAllSeams();
        this.currentHorizontalSeamIndex = -1;
        this.currentVerticalSeamIndex = -1;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("seam is null");
        if (seam.length != height()) throw new IllegalArgumentException("seam is not valid");
        if (width() <= 1) throw new IllegalArgumentException("not sufficient width");
        int prevX = seam[0];
        for (int row = 0; row < height(); row++) {
            int x = seam[row];
            validateX(x);
            if (Math.abs(prevX - x) > 1)
                throw new IllegalArgumentException("index in seam is invalid");
            prevX = x;
            for (int col = x + 1; col < width(); col++)
                picture.set(col - 1, row, picture.get(col, row));
            picture.set(width() - 1, row, Color.WHITE);
        }
        width--;

        fetchAllSeams();
        this.currentHorizontalSeamIndex = -1;
        this.currentVerticalSeamIndex = -1;
    }

    private void validateX(int x) {
        if (x < 0 || x >= width()) throw new IllegalArgumentException("x is out of range");
    }

    private void validateY(int y) {
        if (y < 0 || y >= height()) throw new IllegalArgumentException("y is out of range");
    }

    private double getDelta(Color first, Color second) {
        double rdel = first.getRed() - second.getRed();
        double gdel = first.getGreen() - second.getGreen();
        double bdel = first.getBlue() - second.getBlue();

        return Math.pow(rdel, 2) + Math.pow(gdel, 2) + Math.pow(bdel, 2);
    }

    private void fetchAllSeams() {

        this.seams = new Node[width()][height()];
        for (int i = 0; i < width() * height(); i++) {
            // From top
            int colT = i % width();
            int rowT = i / width();
            if (rowT == 0) {
                if (seams[colT][rowT] == null) seams[colT][rowT] = new Node();
                seams[colT][rowT].distFromTop = MAX_ENERGY;
            }
            if (rowT < height() - 1) {
                if (colT > 0) relaxEdgeVertical(colT, rowT, colT - 1, rowT + 1);
                relaxEdgeVertical(colT, rowT, colT, rowT + 1);
                if (colT < width() - 1) relaxEdgeVertical(colT, rowT, colT + 1, rowT + 1);
            }

            // From left
            int colL = i / height();
            int rowL = i % height();
            if (colL == 0) {
                if (seams[colL][rowL] == null) seams[colL][rowL] = new Node();
                seams[colL][rowL].distFromLeft = MAX_ENERGY;
            }
            if (colL < width() - 1) {
                if (rowL > 0) relaxEdgeHorizontal(colL, rowL, colL + 1, rowL - 1);
                relaxEdgeHorizontal(colL, rowL, colL + 1, rowL);
                if (rowL < height() - 1) relaxEdgeHorizontal(colL, rowL, colL + 1, rowL + 1);
            }
        }
    }

    private void relaxEdgeVertical(int x, int y, int x1, int y1) {
        if (seams[x1][y1] == null) seams[x1][y1] = new Node();
        double energy = seams[x][y].distFromTop + energy(x1, y1);
        if (less(energy, seams[x1][y1].distFromTop)/* ||
                (x1 == x && equal(energy, seams[x1][y1].distFromTop))*/) {
            seams[x1][y1].distFromTop = energy;
            // noinspection SuspiciousNameCombination
            seams[x1][y1].fromTop = x;
        }
    }

    private void relaxEdgeHorizontal(int x, int y, int x1, int y1) {
        if (seams[x1][y1] == null) seams[x1][y1] = new Node();
        double energy = seams[x][y].distFromLeft + energy(x1, y1);
        // noinspection SuspiciousNameCombination
        if (less(energy, seams[x1][y1].distFromLeft)/* ||
                (y1 == y && equal(energy, seams[x1][y1].distFromLeft))*/) {
            seams[x1][y1].distFromLeft = energy;
            // noinspection SuspiciousNameCombination
            seams[x1][y1].fromLeft = y;
        }
    }

    private void printAllEnergy() {
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                StdOut.printf("%9.2f ", energy(j, i));
            }
            StdOut.println();
        }
    }

    private void printSeams() {
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                StdOut.print(seams[j][i] + " ");
            }
            StdOut.println();
        }
    }

    private boolean less(double first, double second) {
        return Double.compare(first, second) < 0;
    }

    private boolean equal(double first, double second) {
        return Double.compare(first, second) == 0;
    }

    private static void printHorizontalSeam(As2SeamCarver carver) {
        int[] horizontalSeam = carver.findHorizontalSeam();
        StdOut.printf("Horizontal seam: { ");
        for (int y : horizontalSeam)
            StdOut.print(y + " ");
        StdOut.println("}");
    }

    private static void printVerticalSeam(As2SeamCarver carver) {
        int[] verticalSeam = carver.findVerticalSeam();
        StdOut.printf("Vertical seam: { ");
        for (int x : verticalSeam)
            StdOut.print(x + " ");
        StdOut.println("}");
    }

    //  unit testing (optional)
    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        As2SeamCarver carver = new As2SeamCarver(picture);

        StdOut.println("Width:" + carver.width() + " Height:" + carver.height());

        StdOut.println("Energy:");
        carver.printAllEnergy();
        StdOut.println();
        StdOut.println("Seams:");
        carver.printSeams();
        StdOut.println();

        printVerticalSeam(carver);
        printHorizontalSeam(carver);


        Picture pic = new Picture(6, 6);

        pic.set(0, 0, new Color(9, 3, 3));
        pic.set(1, 0, new Color(5, 7, 4));
        pic.set(2, 0, new Color(6, 6, 5));
        pic.set(3, 0, new Color(7, 0, 7));
        pic.set(4, 0, new Color(1, 8, 2));
        pic.set(5, 0, new Color(3, 5, 2));

        pic.set(0, 1, new Color(4, 3, 6));
        pic.set(1, 1, new Color(8, 3, 3));
        pic.set(2, 1, new Color(6, 8, 8));
        pic.set(3, 1, new Color(6, 5, 9));
        pic.set(4, 1, new Color(5, 4, 8));
        pic.set(5, 1, new Color(0, 1, 3));

        pic.set(0, 2, new Color(4, 2, 7));
        pic.set(1, 2, new Color(7, 2, 3));
        pic.set(2, 2, new Color(6, 0, 9));
        pic.set(3, 2, new Color(3, 7, 3));
        pic.set(4, 2, new Color(7, 7, 3));
        pic.set(5, 2, new Color(7, 2, 3));

        pic.set(0, 3, new Color(4, 6, 1));
        pic.set(1, 3, new Color(2, 6, 9));
        pic.set(2, 3, new Color(4, 9, 4));
        pic.set(3, 3, new Color(2, 6, 4));
        pic.set(4, 3, new Color(7, 2, 3));
        pic.set(5, 3, new Color(3, 4, 6));

        pic.set(0, 4, new Color(0, 3, 0));
        pic.set(1, 4, new Color(9, 1, 3));
        pic.set(2, 4, new Color(8, 3, 5));
        pic.set(3, 4, new Color(8, 0, 1));
        pic.set(4, 4, new Color(5, 0, 1));
        pic.set(5, 4, new Color(7, 7, 3));

        pic.set(0, 5, new Color(5, 2, 9));
        pic.set(1, 5, new Color(0, 1, 8));
        pic.set(2, 5, new Color(3, 8, 9));
        pic.set(3, 5, new Color(1, 8, 4));
        pic.set(4, 5, new Color(5, 5, 1));
        pic.set(5, 5, new Color(7, 9, 1));

        StdOut.println();
        StdOut.println(pic);

        As2SeamCarver carver1 = new As2SeamCarver(pic);
        StdOut.println();
        StdOut.println("currentVerticalSeam:" + carver1.currentVerticalSeamIndex
                               + " currentHorizontalSeam:" + carver1.currentHorizontalSeamIndex);
        printVerticalSeam(carver1);
        StdOut.println("currentVerticalSeam:" + carver1.currentVerticalSeamIndex
                               + " currentHorizontalSeam:" + carver1.currentHorizontalSeamIndex);
        printHorizontalSeam(carver1);
        StdOut.println("currentVerticalSeam:" + carver1.currentVerticalSeamIndex
                               + " currentHorizontalSeam:" + carver1.currentHorizontalSeamIndex);
        printVerticalSeam(carver1);
    }

}
