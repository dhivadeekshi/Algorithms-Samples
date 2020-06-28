/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.BinaryIn;
import edu.princeton.cs.algs4.BinaryOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Ex43HuffmanCompression {

    private static final int R = 256;

    private static class Node implements Comparable<Node> {
        private final char key;
        private final Node left;
        private final Node right;
        private final int freq;

        public Node(char key, Node left, Node right, int freq) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.freq = freq;
        }

        public boolean isLeafNode() {
            return left == null && right == null;
        }

        public int compareTo(Node that) {
            return Integer.compare(freq, that.freq);
        }
    }

    public void compress(String input, String output) {

        BinaryIn in = new BinaryIn(input);
        BinaryOut out = new BinaryOut(output);

        int chars = 0;
        int[] freq = new int[R];
        while (!in.isEmpty()) {
            chars++;
            freq[in.readChar()]++;
        }

        MinPQ<Node> st = new MinPQ<>();
        for (int i = 0; i < R; i++) {
            if (freq[i] > 0)
                st.insert(new Node((char) i, null, null, freq[i]));
        }

        while (st.size() > 1) {
            Node left = st.delMin();
            Node right = st.delMin();
            st.insert(new Node('.', left, right, left.freq + right.freq));
        }
        Node root = st.delMin();

        BST<Character, String> table = writeTrie(out, root);
        out.write(chars);
        /* StdOut.println("table:");
        for (Character ch : table.keys())
            StdOut.println((ch == '\n' ? "newLine" : ch) + ": " + table.get(ch));*/
        in = new BinaryIn(input);
        while (!in.isEmpty()) {

            char c = in.readChar();
            String s = table.get(c);
            // StdOut.print("char:" + (c == '\n' ? "newLine" : c) + " table:" + s + " = ");
            for (char ch : s.toCharArray()) {
                boolean bit = ch == '1';
                out.write(bit);
                // StdOut.print(bit);
            }
            //    StdOut.println();
        }

        out.close();

    }

    public void expand(String input, String output) {

        BinaryIn in = new BinaryIn(input);
        BinaryOut out = new BinaryOut(output);

        Node root = readTrie(in);
        int chars = in.readInt();
        Node node = root;
        while (!in.isEmpty()) {
            boolean bit = in.readBoolean();
            if (bit) node = node.right;
            else node = node.left;
            if (node.isLeafNode()) {
                out.write(node.key);
                node = root;
                chars--;
                if (chars == 0) break;
            }
        }

        out.close();
    }

    private Node readTrie(BinaryIn in) {
        Node node;
        boolean bit = in.readBoolean();
        if (bit) {
            char ch = in.readChar(8);
            node = new Node(ch, null, null, 0);
            // StdOut.println("char read:" + ch);
        }
        else node = new Node('.', readTrie(in), readTrie(in), 0);
        return node;
    }

    private BST<Character, String> writeTrie(BinaryOut out, Node root) {
        BST<Character, String> table = new BST<>();
        writeTrie(root, out, table, "");
        return table;
    }

    private void writeTrie(Node node, BinaryOut out, BST<Character, String> table, String prefix) {
        if (node == null) return;

        if (node.isLeafNode()) {
            out.write(true);
            out.write(node.key, 8);
            table.put(node.key, prefix);
            char c = node.key;
            //   StdOut.println("key:" + (c == '\n' ? "newLine" : c) + " prefix:" + prefix);
        }
        else {
            out.write(false);
            writeTrie(node.left, out, table, prefix + '0');
            writeTrie(node.right, out, table, prefix + '1');
        }
    }

    private static void binaryDump(int bitsPerLine, String filename) {

        BinaryIn in = new BinaryIn(filename);

        int count, line = 0;
        StdOut.print(line + ": ");
        for (count = 0; !in.isEmpty(); count++) {
            if (bitsPerLine == 0) {
                in.readBoolean();
                continue;
            }
            else if (count != 0 && count % bitsPerLine == 0) {
                StdOut.println();
                StdOut.print(++line + ": ");
            }
            if (in.readBoolean()) StdOut.print(1);
            else StdOut.print(0);
        }
        if (bitsPerLine != 0) StdOut.println();
        StdOut.println(count + " bits");
    }

    public static void main(String[] args) {
        Ex43HuffmanCompression compression = new Ex43HuffmanCompression();
        compression.compress("inputFile.txt", "outputFile.txt");
        compression.expand("outputFile.txt", "inputFileExpanded.txt");

        binaryDump(200, "inputFile.txt");
        binaryDump(200, "inputFileExpanded.txt");
        binaryDump(72, "outputFile.txt");

        try {
            StdOut.print("Original: " + (new In("inputFile.txt").readAll()));
        }
        catch (IllegalArgumentException e) {
            StdOut.println("input file is not present");
        }
        try {
            StdOut.println("Output  : " + (new In("outputFile.txt").readAll()));
        }
        catch (IllegalArgumentException e) {
            StdOut.println("output file is not present");
        }
        try {
            StdOut.print("Expanded: " + (new In("inputFileExpanded.txt").readAll()));
        }
        catch (IllegalArgumentException e) {
            StdOut.println("expanded file is not present");
        }

        try {
            StdOut.println("isSame?" + (new In("inputFile.txt").readAll())
                    .equals(new In("inputFileExpanded.txt").readAll()));
        }
        catch (IllegalArgumentException e) {

        }

    }
}
