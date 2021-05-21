import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

// Seems like "RandomizedQueue" is really a BAG
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] rqueue;
    private int numItems;

    // construct an empty randomized queue
    public RandomizedQueue() {
        rqueue = (Item []) new Object[1];
        numItems = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return numItems == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return numItems;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        int capacity = rqueue.length;
        if (numItems + 1 > capacity) {
            reallocate(capacity*2);
        }
        rqueue[numItems] = item;

        numItems++;
    }

    private void reallocate(int newCapacity) {
        Item[] newUnderlyingArray = (Item []) new Object[newCapacity];
        // Copy over items into new underlying array
        for (int i = 0; i < numItems; i++) {
            newUnderlyingArray[i] = rqueue[i];
        }
        // Make new underlying array the new store for the queue
        rqueue = newUnderlyingArray;
    }

    // remove and return a random item
    public Item dequeue() {
        if (numItems <= 0) {
            throw new java.util.NoSuchElementException();
        }
        int capacity = rqueue.length;
        if (numItems - 1 < capacity / 4) {
            reallocate(capacity / 2);
        }
        swap();
        Item result = rqueue[numItems-1];
        rqueue[numItems-1] = null; // Prevent loitering

        numItems--;
        return result;
    }

    private void swap() {
        if (numItems <= 0) {
            throw new java.util.NoSuchElementException();
        }
        int swapIndex = StdRandom.uniform(numItems);
        Item temp = rqueue[numItems-1];
        rqueue[numItems-1] = rqueue[swapIndex];
        rqueue[swapIndex] = temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (numItems <= 0) {
            throw new java.util.NoSuchElementException();
        }
        Item result = rqueue[StdRandom.uniform(numItems)];
        return result;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        private int currIndex = 0;

        public boolean hasNext() {
            return currIndex < numItems;
        }
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            currIndex++;
            return null;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue rq = new RandomizedQueue<Integer>();
        rq.enqueue(1);
        rq.dequeue();
        if (!rq.isEmpty()) {
            System.out.println("Simple enqueue-dequeue failed!");
        }
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        if (rq.size() != 5) {
            System.out.println("Have incorrect size.");
        }

        System.out.println("Doing some random sampling...");
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.sample());

        int origSize = rq.size();
        System.out.println("Now dequeueing...");
        for (int i = 0; i < origSize; i++) {
            System.out.println(rq.dequeue());
        }

        if (rq.isEmpty()) {
            System.out.println("Great! It got cleared out!");
        }
    }

}
