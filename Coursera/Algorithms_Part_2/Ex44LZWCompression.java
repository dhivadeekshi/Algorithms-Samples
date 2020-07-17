/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryIn;
import edu.princeton.cs.algs4.BinaryOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;

public class Ex44LZWCompression {

    private static final int R = 256;
    private static final int L = 4096;
    private static final int W = 12;

    public void compress(String input, String output) {
        BinaryIn in = new BinaryIn(input);
        BinaryOut out = new BinaryOut(output);

        TST<Integer> table = new TST<>();
        for (int i = 0; i < R; i++)
            table.put((char) i + "", i);
        int count = R + 1;

        String text = in.readString();
        while (!text.isEmpty()) {
            String s = table.longestPrefixOf(text);
            out.write(table.get(s), W);
            text = text.substring(s.length());
            if (count < L && !text.isEmpty()) table.put(s + text.charAt(0), count++);
        }

        out.write(R, W);
        out.close();
    }

    public void expand(String input, String output) {
        BinaryIn in = new BinaryIn(input);
        BinaryOut out = new BinaryOut(output);

        String[] table = new String[L];
        int count = 0;
        for (; count < R; count++)
            table[count] = (char) count + "";
        table[count++] = "";

        String prev = "";
        while (!in.isEmpty()) {
            int value = in.readInt(W);
            if (value == R) break;
            String s = table[value];
            if (s == null) s = prev + prev.charAt(0);
            out.write(s);
            if (!prev.isEmpty() && count < L)
                table[count++] = prev + s.charAt(0);
            prev = table[value];
        }

        out.close();
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

        String inputFile = "mobydick.txt";
        String compressedFile = "compressedFile.txt";
        String expandedFile = "expandedFile.txt";

        Ex44LZWCompression compression = new Ex44LZWCompression();
        compression.compress(inputFile, compressedFile);
        compression.expand(compressedFile, expandedFile);

        binaryDump(200, inputFile);
        binaryDump(200, expandedFile);
        binaryDump(72, compressedFile);

        try {
            StdOut.print("Original: " + (new In(inputFile).readAll()));
        }
        catch (IllegalArgumentException e) {
            StdOut.println("input file is not present");
        }
        try {
            StdOut.println("Output  : " + (new In(compressedFile).readAll()));
        }
        catch (IllegalArgumentException e) {
            StdOut.println("output file is not present");
        }
        try {
            StdOut.print("Expanded: " + (new In(expandedFile).readAll()));
        }
        catch (IllegalArgumentException e) {
            StdOut.println("expanded file is not present");
        }

        try {
            StdOut.println("isSame?" + (new In(inputFile).readAll())
                    .equals(new In(expandedFile).readAll()));
        }
        catch (IllegalArgumentException e) {

        }
    }
}
