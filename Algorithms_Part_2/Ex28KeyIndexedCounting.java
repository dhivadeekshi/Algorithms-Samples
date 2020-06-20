/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class Ex28KeyIndexedCounting {

    public static void KeyIndexCounting(int[] keys, int R) {
        int[] count = new int[R + 1];

        for (int i = 0; i < keys.length; i++)
            count[keys[i] + 1]++;

        for (int i = 0; i < R; i++)
            count[i + 1] += count[i];

        int[] aux = new int[keys.length];
        for (int i = 0; i < keys.length; i++)
            aux[count[keys[i]]++] = keys[i];

        for (int i = 0; i < keys.length; i++)
            keys[i] = aux[i];
    }

    public static void main(String[] args) {
        /* char[] test = {
                'K', 'E', 'Y', 'I', 'N', 'D', 'E', 'X', 'C', 'O', 'U', 'N', 'T', 'I', 'N', 'G'
        };*/

        int[] testInt = { 1, 0, 4, 2, 1, 1, 4, 3, 4, 1, 0 };
        StdOut.print("Original: ");
        for (int i : testInt)
            StdOut.print(i + ",");
        StdOut.println();
        KeyIndexCounting(testInt, 5);
        StdOut.print("Sorted  : ");
        for (int i : testInt)
            StdOut.print(i + ",");
        StdOut.println();
    }
}
