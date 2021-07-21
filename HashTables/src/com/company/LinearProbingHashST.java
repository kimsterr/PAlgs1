package com.company;

public class LinearProbingHashST<Key, Value> {
    private int M = 30001; // Don't worry about dynamic resizing for now
    private Value[] vals = (Value[]) new Object[M];
    private Key[] keys = (Key []) new Object[M];

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public Value get(Key key) {
        for (int x = hash(key); keys[x] != null; x = (x + 1) % M) {
            if (keys[x].equals(key)) {
                return vals[x];
            }
        }

        // key was not found
        return null;
    }

    public void put(Key key, Value val) {
        // Gotcha: what if we fall off of the array?
        int x = hash(key);
        while (keys[x] != null) {
            if (keys[x].equals(key)) {
                break;
            }
            x = (x + 1) % M;
        }

        // This gets run whether or not the key was found
        keys[x] = key;
        vals[x] = val;
    }
}
