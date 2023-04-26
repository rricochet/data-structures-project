package assignment3;

import java.lang.reflect.Array;

/**
 * This class implements multiple stacks
 * that will be stored in a single array.
 * 
 * @author Çağan Atan
 */
public class StackArray<T> {
    private final T[] array; // Main array
    private final int[] top; // The array that stores the indexes of the top values for each stack
    private final int[] next; // The array that stores the index of the next value in the stack if exists,
    private int free; // Stores the value for the next free index in the array

    /**
     * Initialize the object.
     * 
     * @param numberOfStacks The number of stacks that the main array will have in
     *                       it.
     * @param capacity       The capacity of the array that stores the stacks.
     */
    public StackArray(int numberOfStacks, int capacity, Class<T> clazz) {
        array = createGenericArr(clazz, capacity);
        top = new int[numberOfStacks];
        next = new int[capacity];
        free = 0;

        // Initialize all the stack indexes with -1, signifying empty stack
        for (int i = 0; i < numberOfStacks; i++) {
            top[i] = -1;
        }

        // Initialize making the all the indexes point to each other
        for (int i = 0; i < capacity - 1; i++) {
            next[i] = i + 1;
        }
        next[capacity - 1] = -1;
    }

    @SuppressWarnings("unchecked")
    private T[] createGenericArr(Class<T> clazz, int capacity) {
        // We do not serve the main array to other classes, so this is fine
        return (T[]) Array.newInstance(clazz, capacity);
    }

    /**
     * Push a value to a stack.
     * 
     * @param value       Integer value to push.
     * @param stackNumber The index of the stack to push to.
     */
    public void push(T value, int stackNumber) {
        if (free == -1) {
            System.out.println("Stack " + stackNumber + " is full");
            return;
        }
        // Select the current free index with 'index' and move the 'free' to the next
        // free index
        int index = free;
        free = next[index];
        next[index] = top[stackNumber];
        top[stackNumber] = index;
        array[index] = value;
    }

    /**
     * Pop a value from a stack.
     * 
     * @param stackNumber The index of the stack to pop from.
     * @return The removed value from the stack.
     */
    public T pop(int stackNumber) {
        if (top[stackNumber] == -1) {
            System.out.println("Stack " + stackNumber + " is empty");
            return null;
        }
        // Move the index of the top from where it was to next value in the stack
        int index = top[stackNumber];
        top[stackNumber] = next[index];
        next[index] = free;
        free = index;
        return array[index];
    }
}