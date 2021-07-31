import java.util.ArrayDeque;

public class BST<Key extends Comparable<Key>, Value> {
    private Node root;

    private class Node {
        private Key key;
        private Value val;
        private Node left;
        private Node right;
        private int count;

        public Node(Key key, Value val, int count) {
            this.key = key;
            this.val = val;
            left = null;
            right = null;
            this.count = count;
        }
    }

    public void put(Key key, Value val) {
        root = put(key, val, root);
    }
    private Node put(Key key, Value val, Node current) {
        if (current == null) {
            return new Node(key, val, 1);
        }
        int compareScore = key.compareTo(current.key);

        if (compareScore == 0) {
            // Replace
            current.val = val;
            // No new node added, so no need to increase the count
        }
        else if (compareScore < 0) {
            current.left = put(key, val, current.left);
        }
        else if (compareScore > 0) {
            current.right = put(key, val, current.right);
        }
        // The below is WRONG because it will fail on nulls
        //current.count = 1 + current.left.count + current.right.count;

        current.count = 1 + size(current.left) + size(current.right);

        return current;
    }

    // Number of keys less than k
    public int rank(Key k) {
        return rank(k, root);
    }
    private int rank(Key k, Node nd) {
        if (nd == null) return 0;

        int cmp = k.compareTo(nd.key);

        if (cmp == 0) {
            return size(nd.left);
        }
        else if (cmp > 0) {
            return 1 + size(nd.left) + rank(k, nd.right);
        }
        else {
            assert cmp < 0;
            return rank(k, nd.left);
        }
    }

    public Value get(Key key) {
        // Try not to use recursion for this one.
        Node current = root;
        while (current != null) {
            int compareScore = key.compareTo(current.key);
            if (compareScore == 0) {
                return current.val;
            }
            else if (compareScore > 0) {
                current = current.right;
            }
            else {
                assert compareScore < 0;
                current = current.left;
            }
        }
        return null;
    }


    public void delete(Key k) {
        root = delete(k, root);
    }
    private Node delete(Key k, Node x) {
        if (x == null) {
            // The key was not found
            return null;
        }

        int cmp = k.compareTo(x.key);

        if (cmp > 0) {
            x.right = delete(k, x.right);
        }
        else if (cmp < 0) {
            x.left = delete(k, x.left);
        }
        else {  // The key was found!  YAY!  So we have something to delete.
            assert cmp == 0;

            // no child
            if (x.left == null && x.right == null) {
                return null;
            }

            // one child...have the one child be the replacement
            if (x.left == null) return x.right;
            if (x.right == null) return x.left;

            // two children (HARDEST!)
            // the replacement will be the MIN of the right subtree
            Node t = x; // t is pointing to og
            x = findMin(x.right); // x is pointing to min of right subtree
            t.right = delMin(t.right); // cuts out x
            x.left = t.left;
            x.right = t.right;
        }

        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    // General deletion from the root
    public void delMin() { // YES
        root = delMin(root);
    }
    // Delete the minimum node from the subtree rooted at x (could be the entire tree)
    private Node delMin(Node x) { // YES
        if (x == null) return null;
        if (x.left == null) return x.right;

        x.left = delMin(x.left);

        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    private Node findMin(Node x) {
        Node y = x;
        if (y == null) return null;
        while (y != null) {
            y = y.left;
        }
        return y;
    }

    // Largest key <= to given key
    public Key floor(Key key) {
        return floor(key, root);
    }
    private Key floor(Key key, Node node) {
        if (key == null || node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);

        if (cmp == 0) {
            return node.key;
        }
        else if (cmp > 0) {
            // Have a candidate (the current node) but explore right subtree in case even better
            Key otherCandidate = floor(key, node.right);
            if (otherCandidate == null) {
                return node.key;
            }
            else {
                return otherCandidate;
            }
        }
        else {
            assert cmp < 0;
            // Don't even have a candidate yet, so just look at the left subtree
            return floor(key, node.left);
        }
    }

    private Node getMax(Node start) {
        if (start == null) return null;
        Node current = start;
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }
    private Node getMin(Node start) {
        if (start == null) return null;
        Node current = start;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public int size() {
        return size(root);
    }
    private int size(Node nd) {
        if (nd == null) return 0;
        return nd.count;
    }

    public Iterable<Key> iterator() {
        // This would be an inorder traversal.  Left subtree, root, then right subtree
        ArrayDeque<Key> result = new ArrayDeque<Key>();
        inOrder(root, result);
        return result;
    }
    private void inOrder(Node current, ArrayDeque<Key> keys) {
        if (current == null) return;
        inOrder(current.left, keys);
        keys.addLast(current.key);
        inOrder(current.right, keys);
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
