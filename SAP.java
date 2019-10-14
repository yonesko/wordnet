/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class SAP {

    private final Digraph g;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        g = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return sapOnePair(v, w)[1];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return sapOnePair(v, w)[0];
    }

    private int[] sapOnePair(int v, int w) {
        BreadthFirstDirectedPaths vBFS = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths wBFS = new BreadthFirstDirectedPaths(g, w);
        int node = -1;
        int len = Integer.MAX_VALUE;
        for (int i = 0; i < g.V(); i++) {
            if (vBFS.distTo(i) != Integer.MAX_VALUE && wBFS.distTo(i) != Integer.MAX_VALUE
                    && vBFS.distTo(i) + wBFS.distTo(i) < len) {
                len = vBFS.distTo(i) + wBFS.distTo(i);
                node = i;
            }
        }
        return new int[] { node, len };
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return 0;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return 0;
    }

    public static void main(String[] args) {
        SAP sap = new SAP(new Digraph(new In("digraph1.txt")));
        if (sap.ancestor(12, 9) != 5) {
            throw new IllegalArgumentException(sap.ancestor(12, 9) + "");
        }
        if (sap.length(12, 9) != 5) {
            throw new IllegalArgumentException(sap.length(12, 9) + "");
        }
    }
}
