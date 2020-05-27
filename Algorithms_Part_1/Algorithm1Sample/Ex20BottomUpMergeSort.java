/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class Ex20BottomUpMergeSort {
    // Sort in multiples of 2 - 2,4,8,16,....
    public static <Item extends Comparable<Item>> void sort(Item[] a) {
        int N = a.length;
        Item[] aux = (Item[]) new Comparable[N];
        for (int sz = 1; sz < N; sz = sz + sz) {
            for (int lo = 0; lo < N - sz; lo += sz + sz)
                merge(a, aux, lo, lo + sz - 1, Math.min(N - 1, lo + sz + sz - 1));
        }
        /* for (int sz = 1; sz < a.length; sz = sz * 2) {
            int lo = 0;
            while (lo < a.length - sz) {
                int hi = Math.min(lo + 2 * sz - 1, a.length - 1);
                int mid = lo + (hi - lo) / 2;
                merge(a, aux, lo, mid, hi);
                lo = hi + 1;
            }
        }
        merge(a, aux, 0, a.length / 2, a.length - 1);*/
    }

    private static <Item extends Comparable<Item>> boolean less(Item a, Item b) {
        return a.compareTo(b) <= 0;
    }

    private static <Item extends Comparable<Item>> void merge(Item[] a, Item[] aux, int lo,
                                                              int mid,
                                                              int hi) {
        assert (isSorted(a, lo, mid));
        assert (isSorted(a, mid, hi));

        for (int i = lo; i <= hi; i++)
            aux[i] = a[i];

        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }

        assert (isSorted(a, lo, hi));
    }

    private static <Item extends Comparable<Item>> boolean isSorted(Item[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }

    public static void main(String[] args) {
        Integer[] numbers = { 4, 50, 0, 11, -1, 7, -10, 2, 100, 76, -43, -6, 1, 12 };
        Character[] text = {
                'M', 'E', 'R', 'G', 'E', 'S', 'O', 'R', 'T', 'E', 'X', 'A', 'M', 'P', 'L', 'E'
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
