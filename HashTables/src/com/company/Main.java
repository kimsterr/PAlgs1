package com.company;

public class Main {

    public static void main(String[] args) {
        // Usage of the class
        SeparateChainingHashST<String, Integer> hashTableSC = new SeparateChainingHashST<String, Integer>();

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
    }
}
