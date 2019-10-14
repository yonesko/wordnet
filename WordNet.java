/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class WordNet {

    private final Digraph hypernyms;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernymsFileName) {
        if (synsets == null || hypernymsFileName == null) {
            throw new IllegalArgumentException();
        }
        hypernyms = buildHypernyms(hypernymsFileName);
    }

    private Digraph buildHypernyms(String hypernymsFileName) {
        In in = new In(hypernymsFileName);
        String[] lines = in.readAllLines();
        Digraph hypernyms = new Digraph(lines.length);
        for (String line : lines) {
            String[] split = line.split(",");
            int synsetId = Integer.parseInt(split[0]);
            for (int i = 1; i < split.length; i++) {
                hypernyms.addEdge(synsetId, Integer.parseInt(split[1]));
            }
        }
        return hypernyms;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        throw new UnsupportedOperationException();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        throw new UnsupportedOperationException();
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        throw new UnsupportedOperationException();
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
    }
}
