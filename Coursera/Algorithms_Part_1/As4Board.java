/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class As4Board {

    private int[][] tiles;
    private int manhattan = 0;
    private int hamming = 0;
    private int zeroIndex = 0;
    private Stack<As4Board> neighbors = null;
    private As4Board twin = null;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public As4Board(int[][] tiles) {
        int n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
                int index = index(i, j);
                if (tiles[i][j] == 0)
                    zeroIndex = index;
                else if (index != tiles[i][j]) {
                    manhattan += findManhattanFor(tiles[i][j], i, j);
                    hamming++;
                }
            }
        }
    }

    private void findNeighbours() {
        neighbors = new Stack<>();
        int row = row(zeroIndex);
        int col = col(zeroIndex);

        for (int it = 0; it < 4; it++) {
            if (it == 0 && row == 0) continue;
            if (it == 1 && col == 0) continue;
            if (it == 2 && col == dimension() - 1) continue;
            if (it == 3 && row == dimension() - 1) continue;

            int swapRow = row;
            int swapCol = col;
            switch (it) {
                case 0:
                    swapRow--;
                    break;
                case 1:
                    swapCol--;
                    break;
                case 2:
                    swapCol++;
                    break;
                case 3:
                    swapRow++;
                    break;
            }
            swap(zeroIndex, index(swapRow, swapCol));
            As4Board neighbor = new As4Board(tiles);
            neighbors.push(neighbor);
            swap(zeroIndex, index(swapRow, swapCol));
        }
    }

    private int row(int index) {
        return (index - 1) / dimension();
    }

    private int col(int index) {
        return (index - 1) % dimension();
    }

    private int findManhattanFor(int tile, int row, int col) {
        return Math.abs(row(tile) - row) + Math.abs(col(tile) - col);
    }

    private int index(int row, int col) {
        return row * dimension() + col + 1;
    }

    // string representation of this board
    public String toString() {
        StringBuilder board = new StringBuilder();
        board.append(dimension()).append("\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                board.append(tiles[i][j]).append(" ");
            }
            board.append("\n");
        }
        return board.toString();
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0 && manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || y.getClass() != this.getClass()) return false;
        As4Board b = ((As4Board) y);
        int[][] tilesB = b.tiles;
        if (dimension() != b.dimension()) return false;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (tiles[i][j] != tilesB[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<As4Board> neighbors() {
        if (neighbors == null) findNeighbours();
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public As4Board twin() {
        if (twin == null) findTwin();
        return twin;
    }

    private void findTwin() {
        int max = dimension() * dimension() + 1;
        int index1 = zeroIndex;
        while (index1 == zeroIndex) index1 = StdRandom.uniform(1, max);
        int index2 = zeroIndex;
        while (index2 == zeroIndex || index2 == index1)
            index2 = StdRandom.uniform(1, max);

        swap(index1, index2);
        twin = new As4Board(tiles);
        swap(index1, index2);
    }

    private void swap(int index1, int index2) {
        int temp = tiles[row(index1)][col(index1)];
        tiles[row(index1)][col(index1)] = tiles[row(index2)][col(index2)];
        tiles[row(index2)][col(index2)] = temp;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        StdOut.println("Board");

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        As4Board initial = new As4Board(tiles);
        StdOut.println(initial.toString());
        StdOut.println("Manhattan : " + initial.manhattan() + " Hamming : " + initial.hamming());
        StdOut.println(
                "Zero Index : " + initial.zeroIndex + " row:" + initial.row(initial.zeroIndex)
                        + " col:" + initial.col(initial.zeroIndex));
        StdOut.println("IsGoal ? " + initial.isGoal());

        StdOut.println();
        int index = 1;
        for (As4Board neighbour : initial.neighbors()) {
            StdOut.println("Neighbour " + (index++) + ":" + neighbour.toString());
        }

        for (int i = 0; i < 10; i++)
            StdOut.println("Twin " + initial.twin().toString());
    }
}
