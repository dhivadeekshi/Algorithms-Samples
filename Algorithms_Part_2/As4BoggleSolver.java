/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.TST;

public class As4BoggleSolver {
    private static final char IDENTIFIER = '-';
    private static final int MIN_LENGTH = 3;
    private final TST<Integer> dictSet;
    private TST<Integer> rejected;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public As4BoggleSolver(String[] dictionary) {
        dictSet = new TST<>();
        for (int i = 0; i < dictionary.length; i++)
            dictSet.put(dictionary[i], dictionary[i].length());
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(As4BoggleBoard board) {

        rejected = new TST<>();

        int nRows = board.rows();
        int nCols = board.cols();
        int maxLength = nRows * nCols * 2;

        TST<Integer> words = new TST<>();
        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                char c = board.getLetter(row, col);
                boolean isQ = c == 'Q';
                for (String word : dictSet.keysWithPrefix(c + (isQ ? "U" : ""))) {
                    if (!isValidLength(word, maxLength)) rejected.put(word, 0);
                    if (words.contains(word) || rejected.contains(word)) continue;
                    if (isValid(word, board, row, col, isQ))
                        words.put(word, 0);
                }
            }
        }

        return words.keys();
    }

    private boolean isValidLength(String word, int maxLength) {
        int length = word.length();
        /* while (true) {
            int index = word.indexOf("QU");
            if (index == -1) break;
            if (word.length() <= 3) break;
            word = word.substring(index + 2);
            length--;
        }*/
        return length <= maxLength;
    }

    private boolean isValid(String word, As4BoggleBoard board, int row, int col,
                            boolean isQ) {
        if (word.length() < MIN_LENGTH) return false;

        int nRows = board.rows();
        int nCols = board.cols();
        boolean[][] marked = new boolean[nRows][nCols];

        marked[row][col] = true;
        return checkValid(word, board, marked, row, col, 1, isQ);
    }

    private boolean checkValid(String word, As4BoggleBoard board,
                               boolean[][] marked, int row, int col,
                               int d, boolean isQ) {
        if (d >= word.length()) return true;
        if (rejected.contains(word)) return false;
        // StdOut.println("checkValid word:" + word + " row:" + row + " col:" + col + " d" + d);
        char ch = word.charAt(d);
        boolean isU = ch == 'U';
        if (isQ)
            if (isU)
                return checkValid(word, board, marked, row, col, d + 1, false);
            else {
                rejected.put(word, 0);
                return false;
            }
        isQ = ch == 'Q';
        if (isQ && d == word.length() - 1) {
            rejected.put(word, 0);
            return false;
        }

        Bag<Point2D> next = getAdjIndex(ch, board, row, col, marked);
        if (next == null) return false;

        if (next.size() == 1) {
            Point2D adj = next.iterator().next();
            int r = (int) adj.x();
            int c = (int) adj.y();
            marked[r][c] = true;
            return checkValid(word, board, marked, r, c, d + 1, isQ);
        }

        boolean[][] copy = copyMarked(marked, board.rows(), board.cols());
        for (Point2D adj : next) {
            marked = copyMarked(copy, board.rows(), board.cols());
            int r = (int) adj.x();
            int c = (int) adj.y();
            marked[r][c] = true;
            if (checkValid(word, board, marked, r, c, d + 1, isQ)) return true;
        }
        return false;
    }

    private boolean[][] copyMarked(boolean[][] marked, int n, int m) {
        boolean[][] copy = new boolean[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                copy[i][j] = marked[i][j];
        return copy;
    }

    private Bag<Point2D> getAdjIndex(char ch, As4BoggleBoard board, int row, int col,
                                     boolean[][] marked) {
        Bag<Point2D> adjIndices = null;
        for (Point2D adj : adj(row, col)) {
            int r = (int) adj.x(), c = (int) adj.y();
            if (r < 0 || r >= board.rows()) continue;
            if (c < 0 || c >= board.cols()) continue;
            if (marked[r][c]) continue;
            if (board.getLetter(r, c) == ch) {
                if (adjIndices == null) adjIndices = new Bag<>();
                adjIndices.add(adj);
            }
        }
        return adjIndices;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (!dictSet.contains(word)) return 0;
        int wLen = word.length();
        if (wLen > 7) return 11;
        if (wLen > 6) return 5;
        if (wLen > 5) return 3;
        if (wLen > 4) return 2;
        if (wLen > 2) return 1;
        return 0;
    }

    private Iterable<Point2D> adj(int row, int col) {
        Queue<Point2D> queue = new Queue<>();
        queue.enqueue(new Point2D(row - 1, col - 1));
        queue.enqueue(new Point2D(row, col - 1));
        queue.enqueue(new Point2D(row + 1, col - 1));

        queue.enqueue(new Point2D(row - 1, col));
        queue.enqueue(new Point2D(row + 1, col));

        queue.enqueue(new Point2D(row - 1, col + 1));
        queue.enqueue(new Point2D(row, col + 1));
        queue.enqueue(new Point2D(row + 1, col + 1));
        return queue;
    }

    private static void printAllWords(As4BoggleBoard board, As4BoggleSolver solver) {
        int nRows = board.rows();
        int nCols = board.cols();
        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                char c = board.getLetter(row, col);
                boolean isQ = c == 'Q';
                StdOut.print(c + (isQ ? "u" : "") + ":");
                for (String word : solver.dictSet.keysWithPrefix(c + (isQ ? "U" : ""))) {
                    StdOut.print(IDENTIFIER + word + IDENTIFIER);
                }
                StdOut.println();
            }
        }
    }

    public static void main(String[] args) {
        In in = new In("dictionary-16q.txt");
        String[] dictionary = in.readAllStrings();
        Stopwatch watch = new Stopwatch();
        As4BoggleSolver solver = new As4BoggleSolver(dictionary);
        As4BoggleBoard board = new As4BoggleBoard("board-16q.txt");
        StdOut.println("Board:" + board);

        printAllWords(board, solver);

        int score = 0, count = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
            count++;
        }
        StdOut.println(
                "Words = " + count + " Score = " + score + " elapsed:" + watch.elapsedTime());
    }
}
