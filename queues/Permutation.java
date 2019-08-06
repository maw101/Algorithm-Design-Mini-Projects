package queues;

import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

/**
 * Takes an integer k as command line argument; reads a sequence of strings
 * from stdin; prints exactly k of them, uniformly at random.
 */
public class Permutation {

    public static void main(String[] args) {
        final int k = Integer.parseInt(args[0]); // get number of strings to print from command line
        RandomizedQueue<String> queueOfStrings = new RandomizedQueue<>();

        // read strings from std in
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            queueOfStrings.enqueue(item);
        }

        Iterator<String> iterator = queueOfStrings.iterator();

        // print k of the strings
        for (int i = 0; i < k; i++)
            System.out.println(iterator.next());
    }

}