/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Ex17ShuffleSort {
    // Generate a random real number for each array entry
    // Sort the array

    private static class Shuffler<Item> implements Comparable<Shuffler<Item>> {

        private Item item = null;
        private double shufflePriority = 0.0;

        public Shuffler(Item item) {
            SetValue(item);
        }

        public void SetValue(Item item) {
            this.item = item;
            shufflePriority = StdRandom.uniform();
        }

        @Override
        public int compareTo(Shuffler other) {
            return Double.compare(shufflePriority, other.shufflePriority);
        }
    }

    public static <Item> void shuffle(Item[] items) {
        int n = items.length;
        Shuffler<Item>[] shuffler = new Shuffler[n];
        for (int i = 0; i < n; i++)
            shuffler[i] = new Shuffler<Item>(items[i]);
        Ex16ShellSort.sort(shuffler);
        for (int i = 0; i < n; i++)
            items[i] = shuffler[i].item;
    }

    public static void main(String[] args) {

        int n = 26;
        Integer[] numbers = new Integer[n];
        Character[] characters = new Character[n];

        for (int i = 0; i < n; i++) {
            numbers[i] = i;
            int startChar = (int) 'A';
            characters[i] = (char) (startChar + i);
        }

        StdOut.print("Before Shuffling: ");
        for (int i : numbers)
            StdOut.print(i + ",");
        shuffle(numbers);
        StdOut.println();
        StdOut.print("After Shuffling : ");
        for (int i : numbers)
            StdOut.print(i + ",");

        StdOut.println();
        StdOut.println();

        StdOut.print("Before Shuffling: ");
        for (char i : characters)
            StdOut.print(i + ",");
        shuffle(characters);
        StdOut.println();
        StdOut.print("After Shuffling : ");
        for (char i : characters)
            StdOut.print(i + ",");

    }
}
