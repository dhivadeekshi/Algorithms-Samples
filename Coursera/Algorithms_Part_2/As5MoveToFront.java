/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class As5MoveToFront {

    private static final int R = 256;
    private static final int W = 8;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {

        char[] encoder = new char[R];
        for (int i = 0; i < R; i++)
            encoder[i] = (char) i;

        while (!BinaryStdIn.isEmpty()) {
            char ch = BinaryStdIn.readChar(W);
            if (encoder[0] == ch) BinaryStdOut.write(0, W);
            else {
                char replace = encoder[0];
                for (int i = 1; i < R; i++) {
                    char curr = encoder[i];
                    encoder[i] = replace;
                    replace = curr;
                    if (curr == ch) {
                        BinaryStdOut.write(i, W);
                        encoder[0] = replace;
                        break;
                    }
                }
            }
        }
        BinaryStdOut.close();

    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] decoder = new char[R];
        for (int i = 0; i < R; i++)
            decoder[i] = (char) i;

        while (!BinaryStdIn.isEmpty()) {
            int index = BinaryStdIn.readInt(W);
            char ch = decoder[index];
            BinaryStdOut.write(ch, W);
            while (index > 0) {
                decoder[index] = decoder[index - 1];
                index--;
            }
            decoder[0] = ch;
        }

        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException();
    }
}
