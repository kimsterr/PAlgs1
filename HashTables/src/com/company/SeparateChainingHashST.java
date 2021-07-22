package com.company;

public class SeparateChainingHashST<Key, Value> {
    private int M = 97; // # of bins; for simplicity we won't include dynamic resizing
    private Node[] st = new Node[M];

    private static class Node {
        private Object key;
        private Object val;
        private Node next;

        public Node(Object key, Object val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public Value get(Key key) {
        int i = hash(key);
        for (Node currNode = st[i]; currNode != null; currNode = currNode.next) {
            if (key.equals((Key) currNode.key)) {
                return ((Value) currNode.val);
            }
        }
        return null;
    }

    public void put(Key key, Value val) {
        int i = hash(key);

        for (Node currNode = st[i]; currNode != null; currNode = currNode.next) {
            if (key.equals(currNode.key)) {
                currNode.val = val;
                return;
            }
        }

        // we didn't find it
        st[i] = new Node(key, val, st[i]);
    }
}