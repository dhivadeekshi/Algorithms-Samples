/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class Ex16ShellSort {
    // Move entries more than one position at a time by h-sorting the array
    // 3x + 1 sequence

    public static <Item extends Comparable<Item>> void sort(Item[] items) {
        int length = items.length;
        int h = 1;
        while (h * 3 < length) h = h * 3 + 1;

        while (h > 0) {
            for (int i = 0; i < items.length; i += h) {
                int j = i;
                while (j > h - 1 && less(items[j], items[j - h])) {
                    exch(items, j, j - h);
                    j -= h;
                }
            }
            h = (h - 1) / 3;
        }
    }

    private static <Item extends Comparable<Item>> boolean less(Item a, Item b) {
        return a.compareTo(b) < 0;
    }

    private static <Item extends Comparable<Item>> void exch(Item[] items, int i, int j) {
        Item temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }

    public static void main(String[] args) {
        Integer[] numbers = { 4, 50, 0, 11, -1, 7, -10, 2, 100, 76, -43, -6, 1, 12 };
        Character[] text = { 'S', 'O', 'R', 'T', 'E', 'X', 'A', 'M', 'P', 'L', 'E' };

        StdOut.print("Before Sorting: ");
        for (int i : numbers)
            StdOut.print(i + ",");
        sort(numbers);
        StdOut.println();
        StdOut.print("After Sorting : ");
        for (int i : numbers)
            StdOut.print(i + ",");

        StdOut.println();
        StdOut.println();

        StdOut.print("Before Sorting: ");
        for (char i : text)
            StdOut.print(i + ",");
        sort(text);
        StdOut.println();
        StdOut.print("After Sorting : ");
        for (char i : text)
            StdOut.print(i + ",");


    }
}
