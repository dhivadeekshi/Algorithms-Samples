/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Ex45TestAndCompareDataCompressions {

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

    private static int getBits(String filename) {

        BinaryIn in = new BinaryIn(filename);

        int count = 0;
        while (!in.isEmpty()) {
            in.readBoolean();
            count++;
        }
        return count;
    }

    private static void printBitsInfo(String input, String compressed, String result) {
        StdOut.println("Original   : " + getBits(input));
        StdOut.println("Compressed : " + getBits(compressed));
        StdOut.println("Expanded   : " + getBits(result));
    }

    private static boolean isCompressionSuccess(String inputFile, String expandedFile) {
        String input, expanded;
        try {
            In in = new In(inputFile);
            input = in.readAll();
        }
        catch (IllegalArgumentException e) {
            StdOut.println("input file " + inputFile + " is not present");
            input = "-1";
        }
        try {
            In ex = new In(expandedFile);
            expanded = ex.readAll();
        }
        catch (IllegalArgumentException e) {
            StdOut.println("expanded file " + expandedFile + " is not present");
            expanded = "-2";
        }
        return input.equals(expanded);
    }

    public static void main(String[] args) {

        String inputFile = "mobydick.txt";
        String compressedFile = "compressedFile.txt";
        String expandedFile = "expandedFile.txt";

        Stopwatch watch = new Stopwatch();
        Ex42RunLengthCodingCompression rlcc = new Ex42RunLengthCodingCompression();
        rlcc.compress(inputFile, compressedFile);
        rlcc.expand(compressedFile, expandedFile);
        StdOut.println("RLCC    - " + isCompressionSuccess(inputFile, expandedFile) + " : "
                               + watch.elapsedTime());
        printBitsInfo(inputFile, compressedFile, expandedFile);

        watch = new Stopwatch();
        Ex43HuffmanCompression huffman = new Ex43HuffmanCompression();
        huffman.compress(inputFile, compressedFile);
        huffman.expand(compressedFile, expandedFile);
        StdOut.println("Huffman - " + isCompressionSuccess(inputFile, expandedFile) + " : "
                               + watch.elapsedTime());
        printBitsInfo(inputFile, compressedFile, expandedFile);

        watch = new Stopwatch();
        Ex44LZWCompression lzw = new Ex44LZWCompression();
        lzw.compress(inputFile, compressedFile);
        lzw.expand(compressedFile, expandedFile);
        StdOut.println("LZW     - " + isCompressionSuccess(inputFile, expandedFile) + " : "
                               + watch.elapsedTime());
        printBitsInfo(inputFile, compressedFile, expandedFile);
    }
}
