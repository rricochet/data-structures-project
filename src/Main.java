import assignment1.BTree;
import assignment2.HashTable;
import assignment3.StackArray;
import assignment4.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Test implementations for the different assignments
 * 
 * @author Çağan Atan
 * @author Barış Pozlu
 */
public class Main {
    public static void main(String[] args) {

        // ASSIGNMENT 1
        BTree tree = new BTree(2);
        int[] array = { 5, 6, 4, 8, 10, 22, 35, 46, 15, 52 };

        System.out.println("Initial array: " + Arrays.toString(array)); // initial array

        tree.sortArray(array); // sorting the array using a B-Tree after inserting the values

        System.out.println("Sorted array: " + Arrays.toString(array)); // sorted array

        // ASSIGNMENT 2
        System.out.println("------------------------------------------------------------");

        HashTable<String, String> table1 = new HashTable<>(15); // initial capacity is 15 instead of 30 to show that
                                                                // resizing works.
        String filePath = "my_words.txt";

        // code below shows that inserting elements to the table is working as intended

        System.out.println(table1); // initial table
        fillTheTable(table1, filePath);
        System.out.println("table after inserting all the words and their meanings:");
        System.out.println(table1); // table after inserting all the words and their meanings

        System.out.println("\noutput below shows that removing elements from the table is working as intended");

        // code below shows that removing elements from the table is working as intended

        HashTable<String, String> table2 = new HashTable<>();
        table2.insert("testKey1", "testValue1");
        table2.insert("testKey2", "testValue2");
        table2.insert("testKey3", "testValue3");
        table2.insert("testKey4", "testValue4");
        System.out.println(table2); // initial table

        table2.remove("testKey1");
        table2.remove("testKey4");
        System.out.println(table2); // table after removing the first and the last elements

        // code below shows that getting values from the table and checking if a key
        // exists in the table is working as intended
        // it also shows that size is accurate

        System.out.printf("The size of the table is %d\n", table2.size());
        System.out.println("testKey2's value is " + table2.get("testKey2"));
        System.out.println("checking if the table has testKey3: " + table2.containsKey("testKey3"));
        System.out.println("checking if the table has testKey1: " + table2.containsKey("testKey1"));

        // code below shows that getting keys and values is working as intended

        System.out.println("\nkeys are: " + table2.keys());
        System.out.println("\nvalues are: " + table2.values());

        // ASSIGNMENT 3
        System.out.println("------------------------------------------------------------");

        // Modify this to test functionality
        String[][] stacks = { { "3", "2", "1" }, { "c", "b", "a" }, { "m", "l", "k", "z", "y", "x" } };

        // generating the stack array
        StackArray<Object> myStackArray = new StackArray<>(3, 12, Object.class);

        // filling up stack array
        for (int i = 0; i < stacks.length; i++) {
            var stack = stacks[i];
            for (var elem : stack) {
                myStackArray.push(elem, i);
            }
        }

        // trying to push to a full stack
        // should print "Stack 0 is full"
        myStackArray.push("test", 0);

        // emptying and printing the stack array
        for (int i = 0; i < stacks.length; i++) {
            var stackLen = stacks[i].length;
            System.out.print("Stack " + i + ": ");
            for (int j = 0; j < stackLen; j++) {
                System.out.print(myStackArray.pop(i) + " ");
            }
            System.out.println("");
        }

        // trying to pop from an empty stack
        // should print "Stack 0 is empty"
        myStackArray.pop(0);

        // ASSIGNMENT 4
        System.out.println("------------------------------------------------------------");

        Graph<String> graph = new Graph<>();

        // Modify this to test functionality

        graph.addEdge("A", "B", 12);
        graph.addEdge("A", "C", 17);
        graph.addEdge("A", "D", 20);

        graph.addEdge("B", "C", 21);
        graph.addEdge("B", "H", 19);

        graph.addEdge("C", "D", 4);
        graph.addEdge("C", "E", 88);
        graph.addEdge("C", "G", 6);

        graph.addEdge("D", "G", 13);
        graph.addEdge("D", "F", 15);

        graph.addEdge("E", "F", 30);
        graph.addEdge("E", "G", 37);
        graph.addEdge("E", "H", 19);

        graph.addEdge("F", "G", 44);

        // Print normal graph
        System.out.println("Graph:");
        graph.printGraph();
        System.out.println("Total weight:" + graph.getTotalWeight());

        // Get the minimum spanning tree
        Graph<String> mst = graph.getMinimumSpanningTree();

        // Print the minimum spanning tree
        System.out.println("Minimum spanning tree:");
        mst.printGraph();
        System.out.println("Total weight: " + mst.getTotalWeight());
    }

    private static void fillTheTable(HashTable<String, String> table, String filePath) {
        String key = "";
        String value = "";
        File file = new File(filePath);

        try {
            Scanner scanner = new Scanner(file);
            boolean readingKey = true;

            while (scanner.hasNextLine()) {

                String read = scanner.nextLine();

                if (readingKey) {
                    int endIndex = read.indexOf(" ", 6);
                    key = read.substring(6, endIndex);
                }

                else {
                    value = read.substring(9);
                    table.insert(key, value);
                }
                readingKey = !readingKey;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist in the given path.");
        }
    }
}
