public class MaxPQ<Item extends Comparable<Item>> {

    private int M = 9997;
    private Item[] maxPQ = (Item []) new Comparable[M];
    private int size = 0;

    public void insert(Item item) {
        // Insert at end
        maxPQ[++size] = item;

        // Then swim up
        swim(size);
    }

    // Remember, the whole point of the heap is that the dude
    // that you pop off is at the top of the heap!  (Either max or min, in this case max)
    public Item popMax() {
        if (size == 0) {
            throw new IllegalStateException("Cannot delete from empty heap");
        }
        // What we return
        Item result = maxPQ[1];

        // Replace root of heap with end element
        maxPQ[1] = maxPQ[size];

        // Bubble down the end element
        sink(1);

        // Null out the last element to prevent loitering
        maxPQ[size--] = null;

        return result;
    }

    public int getSize() {
        return size;
    }

    // REMEMBER to use the "break" appropriately
    private void sink(int k) {
        while (2*k <= size) {
            // Determining the candidate index
            int j = 2*k;
            if (j < size && maxPQ[j].compareTo(maxPQ[j+1]) < 0) {
                j++;
            }

            // Determine whether to swap and if so, swap
            if (maxPQ[k].compareTo(maxPQ[j]) >= 0) {
                break; // no more need to sink it, we're done
            }
            else {
                // swap and continue
                swap(k, j);
                k = j;
            }
        }
    }

    private void sink(Comparable[] arr, int k, int N) {
        while (2*k <= N) {
            // Determining the candidate index
            int j = 2*k;
            if (j < N && arr[j].compareTo(arr[j+1]) < 0) {
                j++;
            }

            // Determine whether to swap and if so, swap
            if (arr[k].compareTo(arr[j]) >= 0) {
                break; // no more need to sink it, we're done
            }
            else {
                // swap and continue
                swap(arr, k, j);
                k = j;
            }
        }
    }

    private void swim(int k) {
        // NEED ability to stop immediately once determined "swim up" not necessary
        while (k > 1 && maxPQ[k].compareTo(maxPQ[k/2]) > 0) {
            swap(k, k/2);
            k = k / 2;
        }
    }

    private void swap(int i, int j) {
        Item temp = maxPQ[i];
        maxPQ[i] = maxPQ[j];
        maxPQ[j] = temp;
    }

    private void swap(Comparable[] arr, int i, int j) {
        Comparable temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // A heapsort implementation (should be a static method but for convenience of using helper methods...)
    public void heapsort(Comparable[] keys) {
        // Heapify
        Comparable[] temp = new Comparable[keys.length+1];
        for (int i = 0; i < keys.length; i++) {
            temp[i+1] = keys[i];
        }
        for (int i = keys.length / 2 ; i >= 1; i--) {
            sink(temp, i, keys.length);
        }

        // Now we have the max heap!!  Can't just iterate through it index by index
        // but we can strip off the max each time.  We leverage the mechanism!!
        // GOTCHA: the portion that we care about "shrinks" every time on the iteration
        for (int i = keys.length; i > 1; i--) {
            swap(temp, 1, i);
            sink(temp ,1, i-1); // Now sink it
        }

        // Mutate the actual array
        for (int i = 0; i < keys.length; i++) {
            keys[i] = temp[i+1];
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting the testing of Heap object...");
        MaxPQ<Integer> maxIntHeap = new MaxPQ<Integer>();
        maxIntHeap.insert(10);
        maxIntHeap.insert(7);
        maxIntHeap.insert(8);
        maxIntHeap.insert(3);
        assert maxIntHeap.getSize() == 4;
        assert maxIntHeap.popMax() == 10;
        assert maxIntHeap.popMax() == 8;
        assert maxIntHeap.popMax() == 7;
        maxIntHeap.insert(4);
        assert maxIntHeap.popMax() == 4;
        maxIntHeap.insert(9);
        assert maxIntHeap.popMax() == 9;
        maxIntHeap.insert(2);
        maxIntHeap.insert(1);
        assert maxIntHeap.popMax() == 3;
        assert maxIntHeap.popMax() == 2;
        assert maxIntHeap.popMax() == 1;
        assert maxIntHeap.getSize() == 0;
        maxIntHeap.insert(5);
        assert maxIntHeap.popMax() == 5;
        System.out.println("The testing for the Heap object has finished!");

        System.out.println("Start testing for Heapsort...");
        Integer[] myNums = {10, 7, 8, 3, 4, 9, 2, 1, 5, 6};
        maxIntHeap.heapsort(myNums);
        for (int i: myNums) {
            System.out.println(i);
        }
        System.out.println("The testing for Heapsort has finished!");
    }
}
