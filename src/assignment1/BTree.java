package assignment1;

/**
 * A class that implements a B-tree, to be used as a sorting machine.
 * 
 * @author Barış Pozlu
 */
public class BTree {

    private final int T;

    public class Node {
        int n;
        int[] key = new int[2 * T - 1];
        Node[] child = new Node[2 * T];
        boolean leaf = true;
    }

    public BTree(int t) {
        T = t;
        root = new Node();
        root.n = 0;
        root.leaf = true;
    }

    private Node root;

    private void split(Node x, int pos, Node y) {
        Node z = new Node();
        z.leaf = y.leaf;
        z.n = T - 1;
        if (T - 1 >= 0)
            System.arraycopy(y.key, T, z.key, 0, T - 1);
        if (!y.leaf) {
            if (T >= 0)
                System.arraycopy(y.child, T, z.child, 0, T);
        }
        y.n = T - 1;
        if (x.n + 1 - (pos + 1) >= 0)
            System.arraycopy(x.child, pos + 1, x.child, pos + 1 + 1, x.n + 1 - (pos + 1));
        x.child[pos + 1] = z;

        if (x.n - pos >= 0)
            System.arraycopy(x.key, pos, x.key, pos + 1, x.n - pos);
        x.key[pos] = y.key[T - 1];
        x.n = x.n + 1;
    }

    public void insert(final int key) {
        Node r = root;
        if (r.n == 2 * T - 1) {
            Node s = new Node();
            root = s;
            s.leaf = false;
            s.n = 0;
            s.child[0] = r;
            split(s, 0, r);
            insertValue(s, key);
        } else {
            insertValue(r, key);
        }
    }

    private void insertValue(Node x, int k) {

        int i = 0;
        if (x.leaf) {
            for (i = x.n - 1; i >= 0 && k < x.key[i]; i--) {
                x.key[i + 1] = x.key[i];
            }
            x.key[i + 1] = k;
            x.n = x.n + 1;
        } else {
            for (i = x.n - 1; i >= 0 && k < x.key[i]; i--) {
            }
            i++;
            Node tmp = x.child[i];
            if (tmp.n == 2 * T - 1) {
                split(x, i, tmp);
                if (k > x.key[i]) {
                    i++;
                }
            }
            insertValue(x.child[i], k);
        }
    }

    public void sortArray(int[] array) {
        for (int number : array) {
            insert(number);
        }
        sortArray(array, 0, root);
    }

    private int sortArray(int[] array, int insertIndex, Node node) {
        if (node.leaf) {
            for (int i = 0; i < node.n; i++) {
                array[insertIndex++] = node.key[i];
            }
        }

        else {
            insertIndex = sortArray(array, insertIndex, node.child[0]);
            for (int i = 0; i < node.n; i++) {
                array[insertIndex++] = node.key[i];
                insertIndex = sortArray(array, insertIndex, node.child[i + 1]);
            }
        }

        return insertIndex;
    }
}
