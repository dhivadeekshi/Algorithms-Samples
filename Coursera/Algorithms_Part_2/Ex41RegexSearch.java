/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Ex41RegexSearch {

    private final Digraph G;
    private final int M;
    private final char[] re;

    public Ex41RegexSearch(String regExp) {
        M = regExp.length();
        re = regExp.toCharArray();
        G = buildEpisolonTraversalDigraph();
    }

    private Digraph buildEpisolonTraversalDigraph() {
        Digraph g = new Digraph(M + 1);
        Stack<Integer> ops = new Stack<>();

        for (int i = 0; i < M; i++) {
            char c = re[i];
            int lp = i;

            if (c == '(' || c == '|') ops.push(i);
            else if (c == ')') {
                int or = ops.pop();
                if (re[or] == '|') {
                    lp = ops.pop();
                    g.addEdge(lp, or + 1);
                    g.addEdge(or, i);
                }
                else lp = or;
            }

            if (i < M - 1 && re[i + 1] == '*') {
                g.addEdge(lp, i + 1);
                g.addEdge(i + 1, lp);
            }

            if (c == '(' || c == '*' || c == ')')
                g.addEdge(i, i + 1);
        }

        return g;
    }

    public boolean recognizes(String text) {
        DirectedDFS dfs = new DirectedDFS(G, 0);
        Bag<Integer> pc = new Bag<>();
        for (int i = 0; i < G.V(); i++)
            if (dfs.marked(i)) pc.add(i);

        for (int i = 0; i < text.length(); i++) {

            Bag<Integer> matched = new Bag<>();
            for (int v : pc) {
                if (v == M) continue;
                if (re[v] == text.charAt(i) || re[v] == '.')
                    matched.add(v + 1);
            }

            dfs = new DirectedDFS(G, matched);
            pc = new Bag<>();
            for (int v = 0; v < G.V(); v++)
                if (dfs.marked(v)) pc.add(v);
        }

        for (int v : pc)
            if (v == M) return true;
        return false;
    }

    private static void test(String text, Ex41RegexSearch regex) {
        StdOut.println("recognizes " + text + " ? " + regex.recognizes(text));
    }

    public static void main(String[] args) {
        String regx = "((A*B|AC)D)";
        Ex41RegexSearch regex = new Ex41RegexSearch(regx);
        StdOut.println("length:" + regx.length());
        StdOut.println("Graph:" + regex.G);
        test("ABCDE", regex);
        test("ACDE", regex);
        test("BCDE", regex);
        test("ABCDE", regex);
        test("ABCDCDE", regex);
        test("CDAABE", regex);
        test("AABD", regex);
        test("ABD", regex);
        test("ACD", regex);
        test("AAAAAAAAABD", regex);
        test("AABEDDE", regex);

        regex = new Ex41RegexSearch("(.*@.*(com|org))");
        test("dhivakarsubramaniam@gmail.com", regex);
        test("d@gorg", regex);
    }
}
