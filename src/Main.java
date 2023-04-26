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
 */
public class Main {
    public static void main(String[] args) {

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
}
