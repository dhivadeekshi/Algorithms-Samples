/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Ex32KeywordSearch {

    private final int length;
    private String[] suffixes;

    public Ex32KeywordSearch(String s) {
        length = s.length();
        suffixes = new String[length];
        for (int i = 0; i < length; i++) {
            suffixes[i] = s.substring(i);
            // StdOut.println(i + ":" + s.substring(i));
        }

        Arrays.sort(suffixes);
        // for (int i = 0; i < suffixes.length; i++)
        //    StdOut.println(i + ":" + suffixes[i]);
    }

    public int[] find(String query) {
        int index = getindex(query, 0, suffixes.length - 1);
        // StdOut.println("index of " + "\"" + query + "\" is " + index);
        if (index == -1) return null;
        Queue<String> ind = new Queue<>();
        while (index < suffixes.length &&
                query.equals(suffixes[index].substring(0, query.length())))
            ind.enqueue(suffixes[index++]);
        int[] indices = new int[ind.size()];
        int i = 0;
        for (String s : ind)
            indices[i++] = length - s.length();
        Arrays.sort(indices);
        return indices;
    }

    private int getindex(String query, int lo, int hi) {
        if (hi <= lo) return -1;
        int mid = lo + (hi - lo) / 2;
        String checkString = suffixes[mid].substring(0, query.length());
        // StdOut.println("query:" + query + " lo:" + lo + " hi:" + hi + " mid:" + mid + " check:"
        //                       + checkString);
        int cmp = query.compareTo(checkString);
        if (cmp < 0) return getindex(query, lo, mid);
        else if (cmp > 0) return getindex(query, mid, hi);
        else {
            while (mid > 0 && query.equals(suffixes[mid - 1].substring(0, query.length()))) mid--;
            return mid;
        }
    }

    private static void search(Ex32KeywordSearch ks, String test, String query) {
        StdOut.println("------------------------------------------------");
        StdOut.println("Search for " + "\"" + query + "\"");
        int[] indices = ks.find(query);
        int occurrences = indices != null ? indices.length : 0;
        StringBuilder builder = new StringBuilder();
        builder.append(test);
        if (occurrences > 0)
            for (int i = indices.length - 1; i >= 0; i--)
                builder.insert(indices[i], '*');
        if (occurrences == 0) StdOut.println("\"" + query + "\"" + " was not found");
        else {
            StdOut.print(
                    "\"" + query + "\"" + " was found " + indices.length + " times in indices:");
            for (int i = 0; i < indices.length; i++)
                StdOut.print(" " + indices[i]);
        }
        StdOut.println();
        StdOut.println(builder.toString());
    }

    public static void main(String[] args) {
        In in = new In("tale.txt");
        String test = in.readAll();
        Ex32KeywordSearch ks = new Ex32KeywordSearch(test);
        search(ks, test, "was");
        search(ks, test, "age");
        search(ks, test, "time");
        search(ks, test, "dark");
        search(ks, test, "ness");
    }
}
