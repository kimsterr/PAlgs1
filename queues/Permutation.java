import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> myStrings = new RandomizedQueue<>();
        
        while (!StdIn.isEmpty()) {
            myStrings.enqueue(StdIn.readString());
        }

        for (int j = 0; j < k; j++) {
            System.out.println(myStrings.dequeue());
        }
    }
}
