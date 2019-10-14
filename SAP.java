/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.Collections;

public class SAP {

    private final Digraph g;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        g = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return sap(Collections.singletonList(v), Collections.singletonList(w))[1];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return sap(Collections.singletonList(v), Collections.singletonList(w))[0];
    }

    private void validateVertex(Integer v) {
        if (v == null || v < 0 || v >= g.V()) {
            throw new IllegalArgumentException("validateVertex " + v);
        }
    }

    private int[] sap(Iterable<Integer> v, Iterable<Integer> w) {
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
        return new int[] { node, node == -1 ? -1 : len };
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        v.forEach(this::validateVertex);
        w.forEach(this::validateVertex);
        return sap(v, w)[1];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        v.forEach(this::validateVertex);
        w.forEach(this::validateVertex);
        return sap(v, w)[0];
    }

    public static void main(String[] args) {
        SAP sap = new SAP(new Digraph(new In("digraph1.txt")));
        if (sap.ancestor(12, 9) != 5) {
            throw new IllegalArgumentException(sap.ancestor(12, 9) + "");
        }
        if (sap.length(12, 9) != 3) {
            throw new IllegalArgumentException(sap.length(12, 9) + "");
        }
        if (sap.ancestor(Collections.singletonList(12), Collections.singletonList(9)) != 5) {
            throw new IllegalArgumentException(
                    sap.ancestor(Collections.singletonList(12), Collections.singletonList(9)) + "");
        }
        if (sap.length(Collections.singletonList(12), Collections.singletonList(9)) != 3) {
            throw new IllegalArgumentException(
                    sap.length(Collections.singletonList(12), Collections.singletonList(9)) + "");
        }

        SAP sap1 = new SAP(new Digraph(new In("digraph25.txt")));
        if (sap1.ancestor(Arrays.asList(13, 23, 24), Arrays.asList(6, 16, 17)) != 3) {
            throw new IllegalArgumentException(
                    sap1.ancestor(Arrays.asList(13, 23, 24), Arrays.asList(6, 16, 17)) + "");
        }
        if (sap1.length(Arrays.asList(13, 23, 24), Arrays.asList(6, 16, 17)) != 4) {
            throw new IllegalArgumentException(
                    sap1.length(Arrays.asList(13, 23, 24), Arrays.asList(6, 16, 17)) + "");
        }
    }
}
