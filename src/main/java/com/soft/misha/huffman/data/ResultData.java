package com.soft.misha.huffman.data;

import lombok.Data;

import java.util.Map;
import java.util.PriorityQueue;

@Data
public class ResultData {

    private String text;
    private String resultText;
    private String binaryText;
    private String binaryCompressText;
    private PriorityQueue<Node> occurrences;
    private Map<String, String> dictionary;
}

