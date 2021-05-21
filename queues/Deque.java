import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item content;
        Node next;
        Node prev;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item result = current.content;
            current = current.next;
            return result;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private Node makeNode(Item item) {
        Node newNode = new Node();
        newNode.content = item;
        newNode.next = null;
        newNode.prev = null;
        return newNode;
    }

    private void printContents() {
        System.out.print("[ ");
        for (Item i : this ) {
            System.out.print(i);
            System.out.print(",");
        }
        System.out.println(" ]");
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
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
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node newNode = makeNode(item);

        if (size == 0) {
            first = newNode;
            last = newNode;
        }
        else {
            Node oldFirst = first;
            first = newNode;
            first.next = oldFirst;
            oldFirst.prev = first;

            if (size == 1) {
                last = oldFirst;
            }
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node newNode = makeNode(item);

        if (size == 0) {
            first = newNode;
            last = newNode;
        }
        else {
            Node oldLast = last;
            last = newNode;
            last.prev = oldLast;
            oldLast.next = last;

            if (size == 1) {
                first = oldLast;
            }
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        else {
            Item result = first.content;
            if (size == 1) {
                first = null;
                last = null;
            }
            else {
                first = first.next;
                first.prev = null;
            }
            size--;
            return result;
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        else {
            Item result = last.content;
            if (size == 1) {
                first = null;
                last = null;
            }
            else {
                last = last.prev;
                last.next = null;
            }
            size--;
            return result;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        int errorCount = 0;

        // Create new empty Deque of Integer
        Deque<Integer> myNums = new Deque<>();
        System.out.println("Stage 1: Create new empty Deque of Integer");
        myNums.printContents();

        // Add 0 as First, confirm size and contents
        myNums.addFirst(0);
        if (myNums.size() != 1) {
            errorCount++;
        }
        if (myNums.first.content != 0) {
            errorCount++;
        }
        if (myNums.last.content != 0) {
            errorCount++;
        }
        System.out.println("Stage 2:  Add 0 as First");
        myNums.printContents();

        // Remove 0 as First, confirm isEmpty true
        myNums.removeFirst();
        if (!myNums.isEmpty()) {
            errorCount++;
        }
        System.out.println("Stage 3:  Remove 0 as First");
        myNums.printContents();

        // Add 1 as Last, confirm contents and is Empty false
        myNums.addLast(1);
        if (myNums.isEmpty()) {
            errorCount++;
        }
        System.out.println("Stage 4:  Add 1 as Last");
        myNums.printContents();

        // Remove 1 as Last, confirm contents and size
        myNums.removeLast();
        if (myNums.size != 0) {
            errorCount++;
        }
        System.out.println("Stage 5:  Remove 1 as Last");
        myNums.printContents();

        // Add 1 as First and 2 as First, confirm
        myNums.addFirst(1);
        myNums.addFirst(2);
        if (myNums.size != 2) {
            errorCount++;
        }
        System.out.println("Stage 6:  Add 1 as First and 2 as First");
        myNums.printContents();

        // Remove 1 as Last and then remove 2 as Last and then confirm
        if (1 != myNums.removeLast()) {
            errorCount++;
        }
        if (2 != myNums.removeLast()) {
            errorCount++;
        }
        System.out.println("Stage 7:  Remove 1 as Last and then remove 2 as Last");
        myNums.printContents();

        // Add 1 as Last and 2 as Last, confirm
        myNums.addLast(1);
        myNums.addLast(2);
        System.out.println("Stage 8:  Add 1 as Last and 2 as Last");
        myNums.printContents();

        // Remove 1 as First and then remove 2 as Last and then confirm
        if (1 != myNums.removeFirst()) {
            errorCount++;
        }
        if (2 != myNums.removeLast()) {
            errorCount++;
        }
        System.out.println("Stage 9:  Remove 1 as First and then remove 2 as Last");
        myNums.printContents();

        // 2 as Last, 3 as Last, 4 as Last, 1 as First, confirm
        myNums.addLast(2);
        myNums.addLast(3);
        myNums.addLast(4);
        myNums.addFirst(1);
        System.out.println("Stage 10:  2 as Last, 3 as Last, 4 as Last, 1 as First");
        myNums.printContents();

        // Remove 4 as Last, then 3 as Last, then 1 as First and then confirm [Should be left with 2]
        myNums.removeLast();
        myNums.removeLast();
        myNums.removeFirst();
        if (myNums.size() != 1) {
            errorCount++;
        }
        if (myNums.first.content != 2) {
            errorCount++;
        }
        if (myNums.last.content != 2) {
            errorCount++;
        }
        System.out.println("Stage 11:  Remove 4 as Last, then 3 as Last, then 1 as First");
        myNums.printContents();

        // Add 1 as First, 3 as Last, 4 as Last, 0 as First, and then confirm
        myNums.addFirst(1);
        myNums.addLast(3);
        myNums.addLast(4);
        myNums.addFirst(0);
        System.out.println("Stage 12:  Add 1 as First, 3 as Last, 4 as Last, 0 as First");
        myNums.printContents();

        // Remove 4 as Last, remove 0 as first, remove 1 as first, remove 3 as last, remove 2 as first
        myNums.removeLast();
        myNums.removeFirst();
        myNums.removeFirst();
        myNums.removeLast();

        if (myNums.first.content != 2) {
            errorCount++;
        }
        if (myNums.last.content != 2) {
            errorCount++;
        }

        myNums.removeFirst();
        System.out.println("Stage 13:  Remove 4 as Last, remove 0 as first, remove 1 as first, remove 3 as last, remove 2 as first");
        myNums.printContents();

        if (!myNums.isEmpty()) {
            errorCount++;
        }

        System.out.println("Error count: " + errorCount);
    }

}