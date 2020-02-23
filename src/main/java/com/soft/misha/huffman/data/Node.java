package com.soft.misha.huffman.data;

import lombok.Data;

@Data
public class Node{

    private String key;
    private int value;
    private Node left;
    private Node right;
    private boolean leaf;

    public Node() {
    }

    public Node(String key, Integer value) {

        this.key=key;
        this.value = value;
    }

    public Node(String key, int value, boolean leaf) {
        this.key = key;
        this.value = value;
        this.leaf = leaf;
    }

    public Node(String key, int value, Node nodeLeft, Node nodeRight) {
        this.key = key;
        this.value = value;
        left= nodeLeft;
        right= nodeRight;
    }
}
