package queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private final Node head;
    private final Node tail;
    private int size;

    private class Node {
        Item item;
        Node previous;
        Node next;

        Node(Item item) {
            this.item = item;
        }
    }

    // construct an empty deque
    public Deque() {
        head = new Node(null);
        tail = new Node(null);
        size = 0;
        head.next = tail;
        tail.previous = head;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot add a null element");
        Node newNode = new Node(item); // create node for the new first item
        newNode.next = head.next; // set new nodes next node to be the old heads next
        newNode.previous = head; // set the new nodes previous pointer to the lists head
        head.next.previous = newNode; // set the node after the heads previous pointer to be the new node
        head.next = newNode; // set the node after the head to be the new node
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot add a null element");
        Node newNode = new Node(item); // create node for the new last item
        newNode.next = tail; // set new nodes next node to be the tail node
        newNode.previous = tail.previous; // set the new nodes previous pointer to be the previous last item
        tail.previous.next = newNode; // set the node before the tails next pointer to be the new node
        tail.previous = newNode; // set the node before the tail to be the new node
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Deque is empty, no first element");
        Node firstNode = head.next;
        head.next = firstNode.next; // set heads next to be the node after the first node
        head.next.previous = head; // set the new first nodes previous pointer to be the head
        size--; // reduce size
        return firstNode.item; // return first node
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("Deque is empty, no last element");
        Node lastNode = tail.previous;
        tail.previous = lastNode.previous; // set tails previous to be the node before the last node
        tail.previous.next = tail; // set the new last nodes next pointer to be the tail
        size--; // reduce size
        return lastNode.item; // return last node
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

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node currentNode = head;

        @Override
        public boolean hasNext() {
            return currentNode.next != tail;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No more items");
            currentNode = currentNode.next;
            return currentNode.item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation unsupported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        /*
        System.out.println("Tests Follow: ");

        // exception testing
            Deque<Integer> firstDeque = new Deque<>();
            // check remove first on empty list
            try {
                firstDeque.removeFirst();
                System.out.println("removeFirst on empty list FAILED - no exception thrown");
            } catch (Exception e) {
                System.out.println("removeFirst on empty list passed? " + (e instanceof NoSuchElementException));
            }
            // check remove last on empty list
            try {
                firstDeque.removeLast();
                System.out.println("removeLast on empty list FAILED - no exception thrown");
            } catch (Exception e) {
                System.out.println("removeLast on empty list passed? " + (e instanceof NoSuchElementException));
            }
            // check add null item to head
            try {
                firstDeque.addFirst(null);
                System.out.println("addFirst with null item FAILED - no exception thrown");
            } catch (Exception e) {
                System.out.println("addFirst with null item passed? " + (e instanceof IllegalArgumentException));
            }
            // check add null item to tail
            try {
                firstDeque.addLast(null);
                System.out.println("addLast with null item FAILED - no exception thrown");
            } catch (Exception e) {
                System.out.println("addLast with null item passed? " + (e instanceof IllegalArgumentException));
            }
            // check iterator doesn't support remove operation
            try {
                firstDeque.iterator().remove();
                System.out.println("iterator().remove FAILED - no exception thrown");
            } catch (Exception e) {
                System.out.println("iterator().remove resolving in unsupported operation passed? " + (e instanceof UnsupportedOperationException));
            }
            // check iterator returns error when next called on empty list
            try {
                firstDeque.iterator().next();
                System.out.println("iterator().next FAILED - no exception thrown");
            } catch (Exception e) {
                System.out.println("iterator().next resolving in NoSuchElement passed? " + (e instanceof NoSuchElementException));
            }

        // general tests
            Deque<Integer> secondDeque = new Deque<>();
            System.out.println("toString when empty check passed? " + secondDeque.toString().equals("[]"));
            System.out.println("isEmpty check passed? " + secondDeque.isEmpty());
            System.out.println("State of Deque: " + secondDeque.toString());
            secondDeque.addFirst(2);
            System.out.println("State of Deque: " + secondDeque.toString());
            secondDeque.addFirst(1);
            System.out.println("size = 2? " + (secondDeque.size() == 2));
            System.out.println("next passed? " + (secondDeque.iterator().next() == 1));
            System.out.println("State of Deque: " + secondDeque.toString());
            System.out.println("addFirst with two items added in order? " + secondDeque.toString().equals("[1;2]"));

            secondDeque.addLast(3);
            System.out.println("addLast with one item added, passed? " + secondDeque.toString().equals("[1;2;3]"));

            secondDeque.removeLast();
            System.out.println("removeLast with one item removed, passed? " + secondDeque.toString().equals("[1;2]"));

            secondDeque.removeFirst();
            System.out.println("removeFirst with one item removed, passed? " + secondDeque.toString().equals("[2]"));

            secondDeque.removeFirst();
            System.out.println("removeFirst with one item removed, passed? " + secondDeque.toString().equals("[]"));

            System.out.println("iterator().hasNext check passed? " + !secondDeque.iterator().hasNext());
            System.out.println("isEmpty check passed? " + secondDeque.isEmpty());
        */
    }

}