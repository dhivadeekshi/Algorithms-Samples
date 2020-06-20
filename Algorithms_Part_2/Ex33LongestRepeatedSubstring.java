/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Ex33LongestRepeatedSubstring {

    private final int length;
    private final String[] suffixes;

    public Ex33LongestRepeatedSubstring(String s) {
        length = s.length();
        suffixes = new String[length];
        for (int i = 0; i < length; i++)
            suffixes[i] = s.substring(i);

        Arrays.sort(suffixes);
    }

    public int[] findLRS() {
        int index = -1;
        int longest = 0;
        for (int i = 1; i < length; i++) {
            int lcp = lcp(suffixes[i - 1], suffixes[i]);
            if (lcp > longest) {
                longest = lcp;
                index = i - 1;
            }
        }
        if (index == -1) return null;
        StdOut.println("index:" + index + " lcp:" + longest);

        Queue<String> longestStrings = new Queue<>();
        String lString = suffixes[index].substring(0, longest);
        StdOut.println("lString:\"" + lString + "\"");
        for (int i = index; i < length; i++) {
            if (suffixes[i].length() > longest && lString.equals(suffixes[i].substring(0, longest)))
                longestStrings.enqueue(suffixes[i]);
        }


        int[] indices = new int[longestStrings.size()];
        int i = 0;
        for (String s : longestStrings)
            indices[i++] = length - s.length();
        Arrays.sort(indices);
        return indices;
    }

    private int lcp(String a, String b) {
        int n = Math.min(a.length(), b.length());
        for (int i = 0; i < n; i++) {
            if (a.charAt(i) != b.charAt(i)) return i;
        }
        return n;
    }

    public static void main(String[] args) {
        In in = new In("tale.txt");
        String test = in.readAll();
        Ex33LongestRepeatedSubstring lrs = new Ex33LongestRepeatedSubstring(test);

        StringBuilder builder = new StringBuilder();
        builder.append(test);
        int[] indices = lrs.findLRS();
        int occurrences = indices == null ? 0 : indices.length;
        if (occurrences > 0) {
            for (int i = occurrences - 1; i >= 0; i--)
                builder.insert(indices[i], '*');
            StdOut.print("Longest repeated substring was found " +
                                 indices.length + " times in indices:");
            for (int i = 0; i < indices.length; i++)
                StdOut.print(" " + indices[i]);
        }
        else StdOut.println("Longest repeated substring was not found");

        StdOut.println();
        StdOut.println(builder.toString());
    }
}
