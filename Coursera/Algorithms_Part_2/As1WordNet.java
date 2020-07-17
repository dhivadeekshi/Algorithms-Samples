/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class As1WordNet {

    private final HashMap<String, SET<Integer>> nounData;
    private final String[] nounArray;
    private final Digraph wordGraph;
    private final As1SAP sap;

    // constructor takes the name of the two input files
    public As1WordNet(String synsets, String hypernyms) {
        if (synsets == null) throw new IllegalArgumentException("synsets is null");
        if (hypernyms == null) throw new IllegalArgumentException("hypernyms is null");

        String regExpCSV = ",";
        String regExpSpace = " ";

        In vertexIn = new In(synsets);
        String[] lines = vertexIn.readAllLines();
        int v = lines.length;
        wordGraph = new Digraph(v);
        nounData = new HashMap<>();
        nounArray = new String[v];
        for (int i = 0; i < v; i++) {
            String[] info = lines[i].split(regExpCSV);
            String[] _nouns = info[1].split(regExpSpace);
            int id = Integer.parseInt(info[0]);
            nounArray[i] = info[1];

            for (String noun : _nouns) {
                if (nounData.containsKey(noun))
                    nounData.get(noun).add(id);
                else {
                    SET<Integer> nounSet = new SET<>();
                    nounSet.add(id);
                    nounData.put(noun, nounSet);
                }
            }
        }

        In edgeIn = new In(hypernyms);
        while (edgeIn.hasNextLine()) {
            String line = edgeIn.readLine();
            String[] h = line.split(regExpCSV);
            for (int i = 1; i < h.length; i++)
                wordGraph.addEdge(Integer.parseInt(h[0]), Integer.parseInt(h[i]));
        }

        sap = new As1SAP(wordGraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounData.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return nounData.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || !isNoun(nounA)) throw new IllegalArgumentException();
        if (nounB == null || !isNoun(nounB)) throw new IllegalArgumentException();
        return sap.length(nounData.get(nounA), nounData.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || !isNoun(nounA)) throw new IllegalArgumentException();
        if (nounB == null || !isNoun(nounB)) throw new IllegalArgumentException();
        int ancestorIndex = sap.ancestor(nounData.get(nounA), nounData.get(nounB));
        return ancestorIndex == -1 ? "" : nounArray[ancestorIndex];
    }

    private int v() {
        return wordGraph.V();
    }

    private int e() {
        return wordGraph.E();
    }

    // do unit testing of this class
    public static void main(String[] args) {

        String synsets = "synsets.txt";
        String hypernyms = "hypernyms.txt";
        if (args.length > 0) synsets = args[0];
        if (args.length > 1) hypernyms = args[1];

        As1WordNet wordNet = new As1WordNet(synsets, hypernyms);
        for (String noun : wordNet.nouns()) StdOut.println(noun);

        StdOut.println();
        StdOut.println("V:" + wordNet.v());
        StdOut.println("E:" + wordNet.e());

        StdOut.println();
        StdOut.println("is noun \"22-karat_gold\"? " + wordNet.isNoun("22-karat_gold"));
        StdOut.println("is noun \"20-karat_gold\"? " + wordNet.isNoun("20-karat_gold"));

        StdOut.println();
        StdOut.println("distance b/w "
                               + "\"22-karat_gold\" & "
                               + "\"Amur_privet\": "
                               + wordNet.distance("22-karat_gold",
                                                  "Amur_privet"));
        StdOut.println("distance b/w "
                               + "\"22-karat_gold\" & "
                               + "\"18-karat_gold\": "
                               + wordNet.distance("22-karat_gold",
                                                  "18-karat_gold"));
        StdOut.println("distance b/w "
                               + "\"zone\" & "
                               + "\"anatomical_structure\": "
                               + wordNet.distance("zone",
                                                  "anatomical_structure"));

        StdOut.println();
        StdOut.println("ancestor of \"22-karat_gold\" & \"18-karat_gold\": "
                               + wordNet.sap("22-karat_gold", "18-karat_gold"));
    }
}
