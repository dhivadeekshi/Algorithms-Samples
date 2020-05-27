/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Ex23DijkstraThreeWayPartitioning {
    // Let v be partioning item a[lo]
    // Scan i from left to right
    //  (a[i] < v) exchange a[lt] with a[i]; increament both lt and i
    //  (a[i] > v) exchange a[gt] with a[i]; decreament gt
    //  (a[i] == v) increament i

    public static <Item extends Comparable<Item>> void sort(Item[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static <Item extends Comparable<Item>> void sort(Item[] a, int lo, int hi) {
        if (hi <= lo) return;

        int lt = lo, gt = hi, i = lo;
        while (i <= gt) {
            int cmp = a[i].compareTo(a[lt]);
            if (cmp < 0) {
                exch(a, lt, i);
                lt++;
                i++;
            }
            else if (cmp > 0) {
                exch(a, i, gt);
                gt--;
            }
            else i++;
        }

        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
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
        Integer[] numbers = { -1, 50, 0, 11, -1, 7, -10, -1, 100, -1, -43, -6, 1, -1 };
        Character[] text = {
                'S', 'O', 'R', 'T', 'E', 'X', 'A', 'M', 'P', 'L', 'E', 'A', 'A', 'A', 'A'
        };

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
