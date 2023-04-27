import java.util.*;

class Entry<K extends Comparable<K>, V> implements Comparable<Entry<K, V>> {

    int hashCode;
    K key;
    V value;

    Entry(K key, V value) {
        this.key = key;
        this.value = value;
        hashCode = key.hashCode();
    }

    boolean equals(Entry<K, V> otherEntry) {
        if (hashCode != otherEntry.hashCode) return false;
        return key.equals(otherEntry.key);
    }

    @Override
    public String toString() {
        return key + " => " + value;
    }

    @Override
    public int compareTo(Entry<K, V> o) {
        return key.compareTo(o.key);
    }
}



public class HashTable<K extends Comparable<K>, V> implements Iterable<K> {

    private static final int DEFAULT_CAPACITY = 3;
    private static final double DEFAULT_MAX_LOAD_FACTOR = 0.75;

    private int capacity;
    private final double maxLoadFactor;
    private int size, threshold;

    private TreeSet<Entry<K, V>>[] table;

    public HashTable() {
        this(DEFAULT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public HashTable(int capacity) {
        this(capacity, DEFAULT_MAX_LOAD_FACTOR);
    }

    public HashTable(int capacity, double maxLoadFactor) {
        if (capacity <= 0) throw new IllegalArgumentException("Illegal capacity");
        if (maxLoadFactor <= 0) throw new IllegalArgumentException("Illegal maxLoadFactor");

        this.capacity = capacity;
        this.maxLoadFactor = maxLoadFactor;
        threshold = (int) (capacity * maxLoadFactor);
        table = new TreeSet[capacity];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int normalizeIndex(int hashCode) {
        return (hashCode & 0x7FFFFFFF) % capacity;
    }

    public void empty() {
        for (int i = 0; i < capacity; i++) {
            table[i] = null;
        }
        size = 0;
    }

    public boolean containsKey(K key) {
        int index = normalizeIndex(key.hashCode());
        return bucketSeekEntry(key, index) != null;
    }

    private Entry<K, V> bucketSeekEntry(K key, int index) {
        if (key == null) return null;
        TreeSet<Entry<K, V>> bucket = table[index];
        if (bucket == null) return null;
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key))
                return entry;
        }
        return null;
    }

    public V get(K key) {
        if (key == null) return null;

        int index = normalizeIndex(key.hashCode());
        Entry<K, V> entry = bucketSeekEntry(key, index);
        if (entry == null) return null;
        return entry.value;
    }

    private V bucketInsertEntry(Entry<K, V> entry, int index) {
        if (entry == null) return null;
        if (table[index] == null) table[index] = new TreeSet<>();
        TreeSet<Entry<K, V>> bucket = table[index];

        Entry<K, V> existentEntry = bucketSeekEntry(entry.key, index);
        if (existentEntry != null) {
            V oldValue = existentEntry.value;
            existentEntry.value = entry.value;
            return oldValue;
        }
        bucket.add(entry);
        if (++size >= threshold) resizeTable();
        return null;
    }

    public V insert(K key, V value) {
        if (key == null || value == null) return null;
        int index = normalizeIndex(key.hashCode());
        Entry<K, V> entry = new Entry<>(key, value);
        return bucketInsertEntry(entry, index);
    }

    private V bucketRemoveEntry(K key, int index) {
        if (key == null) return null;
        if (table[index] == null) return null;

        Entry<K, V> entry = bucketSeekEntry(key, index);
        if (entry == null) return null;

        V value = entry.value;
        TreeSet<Entry<K, V>> bucket = table[index];
        bucket.remove(entry);
        size--;
        return value;
    }

    public V remove(K key) {
        if (key == null) return null;
        int index = normalizeIndex(key.hashCode());
        return bucketRemoveEntry(key, index);
    }

    private void resizeTable() {
        capacity *= 2;
        threshold = (int) (capacity * maxLoadFactor);

        TreeSet<Entry<K, V>>[] newTable = new TreeSet[capacity];

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                for (Entry<K, V> entry : table[i]) {
                    if (entry != null) {
                        int index = normalizeIndex(entry.hashCode);
                        if (newTable[index] == null) newTable[index] = new TreeSet<>();
                        TreeSet<Entry<K, V>> bucket = newTable[index];
                        bucket.add(entry);
                    }
                }
                table[i].clear();
                table[i] = null;
            }
        }

        table = newTable;
    }

    public List<K> keys() {
        List<K> list = new ArrayList<>();
        for (TreeSet<Entry<K, V>> treeSet : table) {
            if (treeSet != null) {
                for (Entry<K, V> entry : treeSet) {
                    list.add(entry.key);
                }
            }
        }
        return list;
    }

    public List<V> values() {
        List<V> list = new ArrayList<>();
        for (TreeSet<Entry<K, V>> treeSet : table) {
            if (treeSet != null) {
                for (Entry<K, V> entry : treeSet) {
                    list.add(entry.value);
                }
            }
        }
        return list;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        for (TreeSet<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    builder.append(entry).append("\n");
                }
            }
        }
        builder.append("}");
        return builder.toString();
    }

    @Override
    public java.util.Iterator<K> iterator() {
        final int elementCount = size();
        return new java.util.Iterator<K>() {

            int bucketIndex = 0;
            java.util.Iterator<Entry<K, V>> bucketIter = (table[0] == null) ? null : table[0].iterator();

            @Override
            public boolean hasNext() {

                if (elementCount != size) throw new java.util.ConcurrentModificationException();

                if (bucketIter == null || !bucketIter.hasNext()) {

                    while (++bucketIndex < capacity) {
                        if (table[bucketIndex] != null) {
                            java.util.Iterator<Entry<K, V>> nextIter = table[bucketIndex].iterator();
                            if (nextIter.hasNext()) {
                                bucketIter = nextIter;
                                break;
                            }
                        }
                    }
                }
                return bucketIndex < capacity;
            }

            @Override
            public K next() {
                return bucketIter.next().key;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
