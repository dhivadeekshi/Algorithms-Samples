/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class Ex15InsertionSort {
    // In iteration i, swap a[i] with each larger entry to it's left

    public static <Item extends Comparable<Item>> void sort(Item[] items) {
        for (int i = 1; i < items.length; i++) {
            int j = i;
            while (j > 0 && less(items[j], items[j - 1])) {
                exch(items, j, j - 1);
                j--;
            }
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
        Integer[] numbers = { 4, 50, 0, 11, -1, 7, -10 };
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
