/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class WordNet {

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        throw new UnsupportedOperationException();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        throw new UnsupportedOperationException();
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        throw new UnsupportedOperationException();
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {

    }
}
