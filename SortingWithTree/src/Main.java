// Barış Pozlu 210315067 , Çağan Atan 210315025

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        BTree tree = new BTree(2);
        int[] array = {5,6,4,8,10,22,35,46,15,52};

        System.out.println("initial array: " + Arrays.toString(array)); // initial array

        tree.sortArray(array); // sorting the array using a B-Tree after inserting the values

        System.out.println("sorted array: " + Arrays.toString(array)); // sorted array

    }
}
