/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class Ex19MergeSort {
    // Divide array into two halves
    // Recursively sort each half
    // Merge two halves

    public static <Item extends Comparable<Item>> void sort(Item[] items) {
        Item[] aux = (Item[]) new Comparable[items.length];
        sort(items, aux, 0, items.length - 1);
    }

    private static <Item extends Comparable<Item>>
    void sort(Item[] items, Item[] aux, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        sort(items, aux, lo, mid);
        sort(items, aux, mid + 1, hi);
        merge(items, aux, lo, mid, hi);
    }

    private static <Item extends Comparable<Item>> void merge(Item[] items, Item[] aux, int lo,
                                                              int mid, int hi) {
        assert (isSorted(items, lo, mid));
        assert (isSorted(items, mid + 1, hi));

        for (int i = lo; i <= hi; i++)
            aux[i] = items[i];

        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) items[k] = aux[j++];
            else if (j > hi) items[k] = aux[i++];
            else if (less(aux[i], aux[j])) items[k] = aux[i++];
            else items[k] = aux[j++];
        }

        assert (isSorted(items, lo, hi));
    }

    private static <Item extends Comparable<Item>> boolean less(Item a, Item b) {
        return a.compareTo(b) < 0;
    }

    private static <Item extends Comparable<Item>> boolean isSorted(Item[] items, int lo, int hi) {
        for (int i = lo; i < hi; i++) {
            if (less(items[i + 1], items[i])) return false;
        }
        return true;
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
