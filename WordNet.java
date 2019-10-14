/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class WordNet {

    private final Digraph hypernyms;
    private final HashMap<Integer, Set<String>> synsets;

    // constructor takes the name of the two input files
    public WordNet(String synsetsFileName, String hypernymsFileName) {
        if (synsetsFileName == null || hypernymsFileName == null) {
            throw new IllegalArgumentException();
        }
        hypernyms = buildHypernyms(hypernymsFileName);
        synsets = buildSynsets(synsetsFileName);
    }

    private HashMap<Integer, Set<String>> buildSynsets(String synsetsFileName) {
        In in = new In(synsetsFileName);
        String[] lines = in.readAllLines();
        HashMap<Integer, Set<String>> result = new HashMap<>(lines.length);

        for (String line : lines) {
            String[] columns = line.split(",");
            result.put(Integer.parseInt(columns[0]), splitToNouns(columns[1]));
        }
        return result;
    }

    private Set<String> splitToNouns(String s) {
        String[] nouns = s.split(" ");
        HashSet<String> result = new HashSet<>(nouns.length);
        Collections.addAll(result, nouns);
        return result;
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
        return synsets.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        for (Set<String> value : synsets.values()) {
            if (value.contains(word)) {
                return true;
            }
        }
        return false;
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
        if (!wordNet.isNoun("housebreaking")) {
            throw new RuntimeException();
        }
    }
}
