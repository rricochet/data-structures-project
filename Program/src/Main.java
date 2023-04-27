// Barış Pozlu 210315067 , Çağan Atan 210315025

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        HashTable<String, String> table1 = new HashTable<>(15); // initial capacity is 15 instead of 30 to show that resizing works.
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

        // code below shows that getting values from the table and checking if a key exists in the table is working as intended
        // it also shows that size is accurate

        System.out.printf("The size of the table is %d\n", table2.size());
        System.out.println("testKey2's value is " + table2.get("testKey2"));
        System.out.println("checking if the table has testKey3: " + table2.containsKey("testKey3"));
        System.out.println("checking if the table has testKey1: " + table2.containsKey("testKey1"));

        // code below shows that getting keys and values is working as intended

        System.out.println("\nkeys are: " + table2.keys());
        System.out.println("\nvalues are: " + table2.values());
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
        }
        catch (FileNotFoundException e) {
            System.out.println("File does not exist in the given path.");
        }
    }
}
