/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class As4Solver {

    private class Node implements Comparable<Node> {
        private As4Board board;
        private Node previous;
        private int moves;
        private int manhattan;

        public Node(As4Board board, Node previous, int moves) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
            this.manhattan = board.manhattan();
        }

        public Node getPrevious() {
            return previous;
        }

        public int getMoves() {
            return moves;
        }

        public int priority() {
            return manhattan + moves;
        }

        public int compareTo(Node node) {
            return Integer.compare(priority(), node.priority());
        }

        public boolean isGoal() {
            return board.isGoal();
        }

        public Iterable<As4Board> neighbors() {
            return board.neighbors();
        }

        public As4Board getPreviousBoard() {
            return getPrevious().board;
        }

        public As4Board getBoard() {
            return board;
        }
    }

    private Stack<As4Board> solution = null;
    private boolean isUnsolvable = false;
    private boolean isSolvable = false;

    // find a solution to the initial board (using the A* algorithm)
    public As4Solver(As4Board initial) {
        if (initial == null) throw new IllegalArgumentException("Initial board cannot be null");
        MinPQ<Node> pq = new MinPQ<>();
        pq.insert(new Node(initial, null, 0));

        MinPQ<Node> twinPq = new MinPQ<>();
        twinPq.insert(new Node(initial.twin(), null, 0));

        while (!isSolvable && !isUnsolvable) {
            if (solve(pq)) isSolvable = true;
            else if (solve(twinPq)) isUnsolvable = true;
            else if (pq.isEmpty() && twinPq.isEmpty()) isUnsolvable = true;
        }
    }

    private boolean solve(MinPQ<Node> pq) {
        if (pq.isEmpty()) return false;
        Node searchNode = pq.delMin();
        if (searchNode.isGoal()) {
            updateSolution(searchNode);
            return true;
        }
        int moves = searchNode.getMoves() + 1;
        for (As4Board neighbour : searchNode.neighbors()) {
            if (searchNode.getPrevious() != null && neighbour.equals(searchNode.getPreviousBoard()))
                continue;
            pq.insert(new Node(neighbour, searchNode, moves));
        }
        return false;
    }

    private void updateSolution(Node node) {
        solution = new Stack<>();
        while (node != null) {
            solution.push(node.getBoard());
            node = node.getPrevious();
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable && !isUnsolvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        return (isSolvable() ? solution.size() : 0) - 1;
    }

    // sequence of boards in a shortest solution
    public Iterable<As4Board> solution() {
        return isSolvable() ? solution : null;
    }

    // test client (see below)
    public static void main(String[] args) {
        StdOut.println("Solver");

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        As4Board initial = new As4Board(tiles);
        StdOut.println(initial.toString());

        // solve the puzzle
        As4Solver solver = new As4Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (As4Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
