/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Ex18KnuthShuffle {
    // In iteration i, pick integer r between 0 and i uniformly at random
    // Swap a[i] and a[r]

    public static <Item> void shuffle(Item[] items) {
        for (int i = 1; i < items.length; i++) {
            exch(items, i, StdRandom.uniform(i + 1));
        }
    }

    private static <Item> void exch(Item[] items, int i, int j) {
        Item temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }

    public static void main(String[] args) {

        int n = 26;
        Integer[] numbers = new Integer[n];
        Character[] characters = new Character[n];

        for (int i = 0; i < n; i++) {
            numbers[i] = i;
            int startChar = (int) 'A';
            characters[i] = (char) (startChar + i);
        }

        StdOut.print("Before Shuffling: ");
        for (int i : numbers)
            StdOut.print(i + ",");
        shuffle(numbers);
        StdOut.println();
        StdOut.print("After Shuffling : ");
        for (int i : numbers)
            StdOut.print(i + ",");

        StdOut.println();
        StdOut.println();

        StdOut.print("Before Shuffling: ");
        for (char i : characters)
            StdOut.print(i + ",");
        shuffle(characters);
        StdOut.println();
        StdOut.print("After Shuffling : ");
        for (char i : characters)
            StdOut.print(i + ",");


    }
}
