/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class As1Outcast {
    private As1WordNet wordNet;

    public As1Outcast(As1WordNet wordnet)         // constructor takes a WordNet object
    {
        if (wordnet == null) throw new IllegalArgumentException();
        this.wordNet = wordnet;
    }

    public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
    {
        if (nouns == null) throw new IllegalArgumentException();
        String outcast = "";
        int outcastDistance = Integer.MIN_VALUE;
        for (int i = 0; i < nouns.length; i++) {
            int dist = 0;
            for (int j = 0; j < nouns.length; j++)
                dist += wordNet.distance(nouns[i], nouns[j]);
            if (outcastDistance < dist) {
                outcastDistance = dist;
                outcast = nouns[i];
            }
        }
        return outcast;
    }

    public static void main(String[] args)  // see test client below
    {
        String synsets = "synsets.txt";
        String hypernyms = "hypernyms.txt";
        if (args.length > 0) synsets = args[0];
        if (args.length > 1) hypernyms = args[1];

        As1WordNet wordnet = new As1WordNet(synsets, hypernyms);
        As1Outcast outcast = new As1Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
