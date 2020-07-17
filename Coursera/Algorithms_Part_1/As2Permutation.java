/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class As2Permutation {
    public static void main(String[] args) {
        As2RandomizedQueue<String> queue = new As2RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }
        /* for (int i = 1; i < args.length; i++)
            queue.enqueue(args[i]); */
        int k = Integer.parseInt(args[0]);
        Iterator<String> iterator = queue.iterator();
        for (int i = 0; i < k && iterator.hasNext(); i++) {
            StdOut.println(iterator.next());
        }
    }
}
