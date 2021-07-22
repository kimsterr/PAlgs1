package com.company;

public class Main {

    public static void main(String[] args) {
        // Usage of the class
        SeparateChainingHashST<String, Integer> hashTableSC = new SeparateChainingHashST<String, Integer>();

        // Separate chaining tests
        assert hashTableSC.get("cat") == null;
        hashTableSC.put("cat", 1);
        assert hashTableSC.get("cat") != null;
        assert hashTableSC.get("cat") == 1;
        hashTableSC.put("cat", 2);
        assert hashTableSC.get("cat") == 2;
        assert hashTableSC.get("dog") == null;
        hashTableSC.put("dog", 8);
        assert hashTableSC.get("dog") != null;
        assert hashTableSC.get("dog") > 3;
        assert hashTableSC.get("dog") == 8;

        // Usage of the class
        LinearProbingHashST<String, Integer> hashTableLP = new LinearProbingHashST<String, Integer>();

        // Linear probing tests
        assert hashTableLP.get("cat") == null;
        hashTableLP.put("cat", 1);
        assert hashTableLP.get("cat") != null;
        assert hashTableLP.get("cat") == 1;
        hashTableLP.put("cat", 2);
        assert hashTableLP.get("cat") == 2;
        assert hashTableLP.get("dog") == null;
        hashTableLP.put("dog", 8);
        assert hashTableLP.get("dog") != null;
        assert hashTableLP.get("dog") > 3;
        assert hashTableLP.get("dog") == 8;
    }
}
