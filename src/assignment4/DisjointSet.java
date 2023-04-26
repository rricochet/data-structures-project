package assignment4;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A class that implements a disjoint set with path compression and union by
 * rank.
 * 
 * @author Çağan Atan
 * @param <T> Type of the elements in the disjoint set.
 */
public class DisjointSet<T> {
    private final ArrayList<T> elements = new ArrayList<>();
    private final ArrayList<Integer> parents = new ArrayList<>();
    private final ArrayList<Integer> ranks = new ArrayList<>();

    /**
     * Add an element to the disjoint set.
     * 
     * @param element The element to add.
     */
    public void add(T element) {
        if (elements.contains(element))
            return;
        elements.add(element);
        parents.add(elements.size() - 1);
        ranks.add(0);
    }

    /**
     * Find the representative of the set that the element is in
     * and do path compression.
     * 
     * @param element The element to find the representative of.
     * @return The representative of the set that the element is in.
     */
    public T find(T element) {
        int index = elements.indexOf(element);
        // Path compression
        if (parents.get(index) != index) {
            parents.set(index, elements.indexOf(find(elements.get(parents.get(index)))));
        }
        return elements.get(parents.get(index));
    }

    /**
     * Check if two elements are in the same set.
     * 
     * @param element1 The first element.
     * @param element2 The second element.
     * @return True if the elements are in the same set, false otherwise.
     */
    public boolean inSameSet(T element1, T element2) {
        return Objects.equals(find(element1), find(element2));
    }

    /**
     * Union two sets.
     * 
     * @param element1 The first element.
     * @param element2 The second element.
     */
    public void union(T element1, T element2) {
        int index1 = elements.indexOf(find(element1));
        int index2 = elements.indexOf(find(element2));
        if (ranks.get(index1) > ranks.get(index2)) {
            parents.set(index2, index1);
        } else {
            parents.set(index1, index2);
            if (Objects.equals(ranks.get(index1), ranks.get(index2))) {
                ranks.set(index2, ranks.get(index2) + 1);
            }
        }
    }

    /**
     * Get the number of elements in the disjoint set.
     * 
     * @return The number of elements in the disjoint set.
     */
    public int size() {
        return elements.size();
    }
}
