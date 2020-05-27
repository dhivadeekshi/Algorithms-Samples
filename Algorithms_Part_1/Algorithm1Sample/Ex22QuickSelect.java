/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Ex22QuickSelect {
    // Partition the array and find j
    // Move the lo to j + 1 or hi to j - 1 depending on the value

    public static <Item extends Comparable<Item>> Item select(Item[] a, int k) {
        StdRandom.shuffle(a);
        return select(a, k, 0, a.length - 1);
    }

    private static <Item extends Comparable<Item>> Item select(Item[] a, int k, int lo, int hi) {
        int j = partition(a, lo, hi);
        if (j == k) return a[j];
        if (k < j) return select(a, k, lo, j - 1);
        else return select(a, k, j + 1, hi);
    }

    private static <Item extends Comparable<Item>> int partition(Item[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (less(a[++i], a[lo])) if (i == hi) break;
            while (less(a[lo], a[--j])) if (j == lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
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

        StdOut.print("Input: ");
        for (int i : numbers)
            StdOut.print(i + ",");
        StdOut.println();
        int k = StdRandom.uniform(1, numbers.length + 1);
        int result1 = select(numbers, k - 1);
        Ex21QuickSort.sort(numbers);
        StdOut.print("Sorted: ");
        for (int i : numbers)
            StdOut.print(i + ",");
        StdOut.println();
        StdOut.print("select " + k + " : " + result1);

        StdOut.println();

        StdOut.print("Input: ");
        for (char i : text)
            StdOut.print(i + ",");
        StdOut.println();
        k = StdRandom.uniform(1, text.length + 1);
        char result2 = select(text, k - 1);
        Ex21QuickSort.sort(text);
        StdOut.print("Sorted: ");
        for (char i : text)
            StdOut.print(i + ",");
        StdOut.println();
        StdOut.print("select " + k + " : " + result2);
    }
}
