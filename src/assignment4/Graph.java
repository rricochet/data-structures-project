package assignment4;

import java.util.ArrayList;

/**
 * A class that implements a graph, with a method
 * to turn the graph into an MST, with Kruskal's algorithm.
 * 
 * @author Çağan Atan
 * @param <T> Type of the elements in the graph.
 */
public class Graph<T> {
    private final ArrayList<Edge<T>> edges = new ArrayList<>();

    private static class Edge<E> implements Comparable<Edge<E>> {
        int weight;
        E vertex1, vertex2;

        Edge(E vertex1, E vertex2, int weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge<E> o) {
            return weight - o.weight;
        }

        @Override
        public String toString() {
            return vertex1 + " <-> " + vertex2 + ": " + weight;
        }
    }

    /**
     * Add an edge to the graph.
     * 
     * @param vertex1 The first vertex of the edge.
     * @param vertex2 The second vertex of the edge.
     * @param weight  The weight of the edge.
     */
    public void addEdge(T vertex1, T vertex2, int weight) {
        edges.add(new Edge<>(vertex1, vertex2, weight));
    }

    /**
     * Get the minimum spanning tree of the graph with Kruskal's algorithm.
     * 
     * @return The minimum spanning tree of the graph.
     */
    public Graph<T> getMinimumSpanningTree() {
        Graph<T> mst = new Graph<>();
        // Step 0: Initialize the disjoint set
        DisjointSet<T> disjointSet = new DisjointSet<>();
        for (Edge<T> edge : edges) {
            disjointSet.add(edge.vertex1);
            disjointSet.add(edge.vertex2);
        }
        // Step 1: Sort the edges in non-decreasing order of their weights
        edges.sort(Edge::compareTo);

        for (Edge<T> edge : edges) {

            // Step 2: If the edge does not form a cycle, add it to the MST
            if (disjointSet.inSameSet(edge.vertex1, edge.vertex2))
                continue;
            disjointSet.union(edge.vertex1, edge.vertex2);
            mst.addEdge(edge.vertex1, edge.vertex2, edge.weight);

            // Step 3: If the MST has |V| - 1 edges, stop
            if (mst.edges.size() == disjointSet.size() - 1)
                break;
        }
        return mst;
    }

    /**
     * Print graph.
     */
    public void printGraph() {
        edges.sort(Edge::compareTo);
        for (Edge<T> edge : edges) {
            System.out.println(edge);
        }
    }

    /**
     * Get the total weight of the graph.
     * 
     * @return The total weight of the graph.
     */
    public int getTotalWeight() {
        int totalWeight = 0;
        for (Edge<T> edge : edges) {
            totalWeight += edge.weight;
        }
        return totalWeight;
    }
}