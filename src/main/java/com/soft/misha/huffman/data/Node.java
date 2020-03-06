package com.soft.misha.huffman.data;

import lombok.Data;

@Data
public class Node{

    private String key;
    private int value;
    private Node left;
    private Node right;

    public Node(String key, Integer value) {
        this.key=key;
        this.value = value;
    }
}
