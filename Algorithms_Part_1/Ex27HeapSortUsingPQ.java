/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class Ex27HeapSortUsingPQ {
    // Create max-heap with all N keys, using the bottom up sink method
    // Repeatedly remove the max key

    public static <Item extends Comparable<Item>> void sort(Item[] a) {
        int N = a.length;
        for (int i = N / 2; i >= 0; i--)
            sink(a, i, N);
        for (int i = N - 1; i >= 0; i--) {
            exch(a, 0, i);
            sink(a, 0, i);
        }
    }

    private static <Item extends Comparable<Item>> void sink(Item[] a, int i, int N) {
        int child = (i + 1) * 2 - 1;
        if (child >= N) return;
        if (child + 1 < N && less(a[child], a[child + 1])) child++;
        if (less(a[i], a[child])) {
            exch(a, child, i);
            sink(a, child, N);
        }
    }

    private static <Item extends Comparable<Item>> boolean less(Item a, Item b) {
        return a.compareTo(b) <= 0;
    }

    private static <Item extends Comparable<Item>> void exch(Item[] a, int i, int j) {
        Item temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        Integer[] numbers = { 4, 50, 0, 11, -1, 7, -10, 2, 100, 76, -43, -6, 1, 12 };
        Character[] text = { 'S', 'O', 'R', 'T', 'E', 'X', 'A', 'M', 'P', 'L', 'E' };

        StdOut.print("Before Sorting: ");
        for (int i : numbers)
            StdOut.print(i + ",");
        StdOut.println();
        sort(numbers);
        StdOut.print("After Sorting : ");
        for (int i : numbers)
            StdOut.print(i + ",");

        StdOut.println();
        StdOut.println();

        StdOut.print("Before Sorting: ");
        for (char i : text)
            StdOut.print(i + ",");
        StdOut.println();
        sort(text);
        StdOut.print("After Sorting : ");
        for (char i : text)
            StdOut.print(i + ",");
    }
}
