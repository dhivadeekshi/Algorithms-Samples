/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.DepthFirstOrder;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class As1SAP {

    private final Digraph digraph;
    private final DepthFirstOrder order;

    // constructor takes a digraph (not necessarily a DAG)
    public As1SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();
        digraph = new Digraph(G.V());
        for (int i = 0; i < G.V(); i++)
            for (int adj : G.adj(i))
                digraph.addEdge(i, adj);

        order = new DepthFirstOrder(digraph);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateVetex(v);
        validateVetex(w);
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);
        return findAncestorAndLength(v, w, bfsV, bfsW)[1];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateVetex(v);
        validateVetex(w);
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);
        return findAncestorAndLength(v, w, bfsV, bfsW)[0];
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v);
        validateVertices(w);
        int sortest = Integer.MAX_VALUE;
        HashMap<Integer, BreadthFirstDirectedPaths> bfsW = new HashMap<>();
        for (int vv : v) {
            BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, vv);
            for (int ww : w) {
                if (!bfsW.containsKey(ww))
                    bfsW.put(ww, new BreadthFirstDirectedPaths(digraph, ww));
                int length = findAncestorAndLength(vv, ww, bfsV, bfsW.get(ww))[1];
                if (length >= 0 && length < sortest) sortest = length;
            }
        }
        return sortest == Integer.MAX_VALUE ? -1 : sortest;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v);
        validateVertices(w);
        int sortest = Integer.MAX_VALUE;
        int sortestAncestor = -1;
        HashMap<Integer, BreadthFirstDirectedPaths> bfsW = new HashMap<>();
        for (int vv : v) {
            BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, vv);
            for (int ww : w) {
                if (!bfsW.containsKey(ww))
                    bfsW.put(ww, new BreadthFirstDirectedPaths(digraph, ww));
                int[] ancestorAndLength = findAncestorAndLength(vv, ww, bfsV, bfsW.get(ww));
                int length = ancestorAndLength[1];
                if (length < sortest) {
                    sortest = length;
                    sortestAncestor = ancestorAndLength[0];
                }
            }
        }
        return sortestAncestor;
    }

    private void validateVetex(int v) {
        if (v < 0 || v > digraph.V()) throw new IllegalArgumentException();
    }

    private void validateVertices(Iterable<Integer> v) {
        if (v == null) throw new IllegalArgumentException();
        for (Integer i : v) {
            if (i == null) throw new IllegalArgumentException();
            validateVetex(i);
        }
    }

    private int[] findAncestorAndLength(int v, int w, BreadthFirstDirectedPaths bfsV,
                                        BreadthFirstDirectedPaths bfsW) {
        int sortest = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i : order.post()) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                int length = 0;
                for (int pathW : bfsW.pathTo(i))
                    length++;
                for (int pathV : bfsV.pathTo(i))
                    length++;
                if (length < sortest) {
                    sortest = length;
                    ancestor = i;
                }
            }
        }
        return new int[] { ancestor, sortest == Integer.MAX_VALUE ? -1 : sortest - 2 };
    }

    private static void testSap(As1SAP sap, int v, int w) {
        try {
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
        catch (IllegalArgumentException e) {
            StdOut.println("IllegalArgumentException ocurred");
        }
        StdOut.println();
    }

    private static void testSapVertices(As1SAP sap, int[] v, int[] w) {
        try {
            Queue<Integer> vv = new Queue<>();
            Queue<Integer> ww = new Queue<>();
            StdOut.print("{ ");
            for (int i = 0; i < v.length; i++) {
                vv.enqueue(v[i]);
                StdOut.print(v[i] + ",");
            }
            StdOut.print(" } { ");
            for (int i = 0; i < w.length; i++) {
                ww.enqueue(w[i]);
                StdOut.print(w[i] + ",");
            }
            StdOut.println(" }");

            int length = sap.length(vv, ww);
            int ancestor = sap.ancestor(vv, ww);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
        catch (IllegalArgumentException e) {
            StdOut.println("IllegalArgumentException ocurred");
        }
        StdOut.println();
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String file = "digraph.txt";
        file = "digraph-wordnet.txt";
        // file = "digraph9.txt";
        if (args.length > 0) file = args[0];
        In in = new In(file);
        Digraph G = new Digraph(in);
        As1SAP sap = new As1SAP(G);

        if (args.length <= 0) {
            // int[] textCase = { 3, 11, 9, 12, 7, 2, 1, 6 }
            int[] textCase = {
                    11255, 58726,
                    23226, 65035,
                    3, 5,
                    3, 10,
                    1, 10,
                    10, 1,
                    10, 3,
                    3, 1,
                    1, 3
            };
            int i = 0;
            while (i < textCase.length - 1) {
                int v = textCase[i++];
                int w = textCase[i++];
                StdOut.println(v + " " + w);
                testSap(sap, v, w);
            }

            testSapVertices(sap, new int[] { 11255 }, new int[] { 58726 });
            testSapVertices(sap, new int[] { 66452 }, new int[] { 25755, 63194 });
            testSapVertices(sap, new int[] { 39328, 49766 }, new int[] { 46710 });
            testSapVertices(sap, new int[] { 26538, 81832 }, new int[] { 6455, 44446 });
        }
        else {
            while (!StdIn.isEmpty()) {
                int v = StdIn.readInt();
                int w = StdIn.readInt();
                testSap(sap, v, w);
            }
        }
    }
}
