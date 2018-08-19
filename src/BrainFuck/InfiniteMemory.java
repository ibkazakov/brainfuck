package BrainFuck;

public class InfiniteMemory {
    private char[] memory;
    private int max_positive_index;
    private int min_negative_index;


    public InfiniteMemory(int capacity) {
        max_positive_index = capacity;
        min_negative_index = -capacity;
        memory = new char[2*capacity + 1];
    }

    private void doubleCapacity() {
        char[] newMemory = new char[2*(max_positive_index - min_negative_index) + 1];
        System.arraycopy(memory, 0, newMemory, -min_negative_index, memory.length);
        max_positive_index *= 2;
        min_negative_index *= 2;
        memory = newMemory; // delete memory
    }

    private void ensureCapacity(int index) {
        while (!((min_negative_index <= index) && (index <= max_positive_index))) {
            doubleCapacity();
        }
    }

    public void set(int index, char value) {
        ensureCapacity(index);
        memory[index - min_negative_index] = value;
    }

    public char get(int index) {
        ensureCapacity(index);
        return memory[index - min_negative_index];
    }

}
