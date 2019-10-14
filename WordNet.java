/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class WordNet {

    private final Digraph hypernyms;
    private final HashMap<Integer, Set<String>> synsets;
    private final HashMap<String, Set<Integer>> synsets2;

    // constructor takes the name of the two input files
    public WordNet(String synsetsFileName, String hypernymsFileName) {
        if (synsetsFileName == null || hypernymsFileName == null) {
            throw new IllegalArgumentException();
        }
        hypernyms = buildHypernyms(hypernymsFileName);
        validaDAGIsRooted(hypernyms);
        synsets = buildSynsets(synsetsFileName);
        synsets2 = buildSynsets2(synsetsFileName);
    }

    private void validaDAGIsRooted(Digraph digraph) {
        if (hasCycle(digraph)) {
            throw new IllegalArgumentException("hasCycle");
        }
        if (!hasRoot(digraph)) {
            throw new IllegalArgumentException("has no root");
        }
    }

    private boolean hasRoot(Digraph digraph) {
        int roots = 0;
        for (int i = 0; i < digraph.V(); i++) {
            if (digraph.indegree(i) != 0 && digraph.outdegree(i) == 0) {
                roots++;
            }
        }
        return roots == 1;
    }

    private boolean hasCycle(Digraph digraph) {
        Queue<Integer> queue = new Queue<>();
        boolean marked[] = new boolean[digraph.V()];
        queue.enqueue(0);
        while (!queue.isEmpty()) {
            Integer val = queue.dequeue();
            if (marked[val]) {
                return false;
            }
            marked[val] = true;
            for (Integer adj : digraph.adj(val)) {
                queue.enqueue(adj);
            }
        }
        return true;
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

    private HashMap<String, Set<Integer>> buildSynsets2(String synsetsFileName) {
        In in = new In(synsetsFileName);
        String[] lines = in.readAllLines();
        HashMap<String, Set<Integer>> result = new HashMap<>(lines.length);

        for (String line : lines) {
            String[] columns = line.split(",");
            for (String noun : splitToNouns(columns[1])) {
                result.putIfAbsent(noun, new HashSet<>());
                result.get(noun).add(Integer.parseInt(columns[0]));
            }
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
                hypernyms.addEdge(synsetId, Integer.parseInt(split[i]));
            }
        }
        return hypernyms;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synsets2.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return synsets2.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        return new SAP(hypernyms).length(synsets2.get(nounA), synsets2.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        return String.join(" ", synsets.get(
                new SAP(hypernyms).ancestor(synsets2.get(nounA), synsets2.get(nounB))));
    }

    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
        if (!wordNet.isNoun("housebreaking")) {
            throw new RuntimeException();
        }

        if (wordNet.distance("tenderfoot", "handbow") != 13) {
            throw new RuntimeException();
        }

        if (wordNet.distance("cappelletti", "alphavirus") != 11) {
            throw new RuntimeException();
        }

        WordNet net = new WordNet("synsets11.txt", "hypernyms11AmbiguousAncestor.txt");
        if (net.distance("a", "g") != 4) {
            System.out.println(toDot(net.hypernyms));
            throw new RuntimeException();
        }
        //noinspection ResultOfObjectAllocationIgnored
        new WordNet("synsets11.txt", "hypernyms3InvalidCycle.txt");
    }

    private static String toDot(Digraph digraph) {
        StringBuilder sb = new StringBuilder("digraph G {\n");

        for (int i = 0; i < digraph.V(); i++) {
            for (Integer integer : digraph.adj(i)) {
                sb.append(String.format("\"%s\" -> \"%s\"\n", i, integer));
            }
        }

        sb.append('}');
        return sb.toString();
    }
}
