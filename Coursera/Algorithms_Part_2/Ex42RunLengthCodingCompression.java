/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryIn;
import edu.princeton.cs.algs4.BinaryOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Ex42RunLengthCodingCompression {

    private static final int cLen = 4;
    private static final int MAX = (int) (Math.pow(2, cLen) - 1);

    public void compress(String input, String output) {
        BinaryIn in = new BinaryIn(input);
        BinaryOut out = new BinaryOut(output);
        boolean bit = false;
        int count = 0;
        while (!in.isEmpty()) {

            boolean cBit = in.readBoolean();
            if (cBit != bit || count == MAX) {
                out.write(count, cLen);
                if (cBit == bit) out.write(0, cLen);
                count = 1;
            }
            else count++;
            bit = cBit;
        }
        out.close();
    }

    public void expand(String input, String output) {
        BinaryIn in = new BinaryIn(input);
        BinaryOut out = new BinaryOut(output);
        boolean bit = false;
        while (!in.isEmpty()) {
            int len = in.readInt(cLen);
            for (int i = 0; i < len; i++) out.write(bit);
            bit = !bit;
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
        Ex42RunLengthCodingCompression compression = new Ex42RunLengthCodingCompression();
        compression.compress("inputFile.txt", "outputFile.txt");
        compression.expand("outputFile.txt", "inputFileExpanded.txt");

        binaryDump(200, "inputFile.txt");
        binaryDump(200, "inputFileExpanded.txt");
        binaryDump(cLen, "outputFile.txt");

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

        StdOut.println("MAX:" + MAX);
    }
}
