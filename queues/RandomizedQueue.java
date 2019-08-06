package queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int endIndex;
    private Item[] arr;

    // construct an empty randomized queue
    public RandomizedQueue() {
        arr = (Item[]) new Object[2]; // must cast due to java limitations with generics
        endIndex = -1;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return endIndex + 1;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot add a null element");
        if (size() == arr.length) // if array full
          resizeArray(2 * arr.length);
        endIndex++;
        arr[endIndex] = item; // add item to queue
    }

    // remove and return a random item
    public Item dequeue() {
        int rand;
        if (isEmpty())
          throw new NoSuchElementException("RandomizedQueue is empty");
        rand = StdRandom.uniform(endIndex + 1);
        Item removedItem = arr[rand];
        // now we have removed an item, move item in end index into its position and reduce size of list
        arr[rand] = arr[endIndex];
        arr[endIndex] = null;
        endIndex--; // reduce known end index
        // if array <= 25% full, resize
        if (!isEmpty() && (size() <= (arr.length / 4)))
            resizeArray(arr.length / 2);
        return removedItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int rand;
        if (isEmpty())
            throw new NoSuchElementException("RandomizedQueue is empty");
        rand = StdRandom.uniform(endIndex + 1);
        return arr[rand];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator(); // as we need to make a copy of list each time, require newly initialised iterator
    }

    private class RandomIterator implements Iterator<Item> {
        private int endIndexCopy;
        private Item[] arrCopy;

        public RandomIterator() {
            arrCopy = (Item[]) new Object[endIndex + 1]; // must cast due to java limitations with generics
            // copy each item in turn into the new 'copied' array
            for (int index = 0; index <= endIndex; index++)
                arrCopy[index] = arr[index];
            // make copy of the main arrays end index for the new 'copied' array
            endIndexCopy = endIndex;
        }

        @Override
        public boolean hasNext() {
            return endIndexCopy >= 0;
        }

        @Override
        public Item next() {
            int rand;
            if (!hasNext())
                throw new NoSuchElementException("No more items");
            rand = StdRandom.uniform(endIndexCopy + 1);
            Item removedItem = arrCopy[rand];
            // now we have removed an item, move item in end index into its position and reduce size of list
            arrCopy[rand] = arrCopy[endIndexCopy];
            arrCopy[endIndexCopy] = null;
            endIndexCopy--; // reduce known end index
            return removedItem;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation unsupported");
        }
    }

    /*
    public String toString() {
        String result = "";
        if (isEmpty())
            return "[]";
        for (Item item : this)
            result += ";" + item;
        return "[" + result.substring(1) + "]"; // print formatted result without the first semi colon
    }
    */

    private void resizeArray(int newCapacity) {
        Item[] newArr = (Item[]) new Object[newCapacity];
        for (int i = 0; i <= endIndex; i++)
            newArr[i] = arr[i];
        arr = newArr;
    }

    public static void main(String[] args) {
        // tests
    }

}