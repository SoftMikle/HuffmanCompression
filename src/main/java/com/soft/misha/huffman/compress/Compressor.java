package com.soft.misha.huffman.compress;

import com.soft.misha.huffman.data.Node;
import lombok.Data;

import java.util.*;


public class Compressor {
    private String text;

    public Map<Character, Integer> calcOccurrence() {
        var occurrences = new HashMap<Character, Integer>();

        for (char c : text.toCharArray()) {
            if (occurrences.containsKey(c)) {
                occurrences.put(c, occurrences.get(c) + 1);
            } else {
                occurrences.put(c, 1);
            }
        }
        return occurrences;
    }

    public PriorityQueue<Node> calcOccurrenceNode() {
        Comparator<Node> nodeComparator = Comparator.comparingInt(Node::getValue);
        var nodes = new PriorityQueue<>(nodeComparator);
        calcOccurrence().forEach((key, value) -> nodes.add(new Node(key.toString(), value)));
        return nodes;
    }

    public Node buildHuffmanTree() {
        var occurrence = calcOccurrenceNode();
        while (occurrence.size() > 1) {
            Node nodeLeft = occurrence.poll();
            Node nodeRight = occurrence.poll();
            Node node = new Node(nodeLeft.getKey() + nodeRight.getKey(), nodeLeft.getValue() + nodeRight.getValue());
            node.setLeft(nodeLeft);
            node.setRight(nodeRight);
            occurrence.add(node);
        }
        var root = occurrence.poll();
        return root;
    }

    public Map<String, String> buildDictionary() {
        HashMap<String, String> dictionary = new HashMap<>();
        Node node = buildHuffmanTree();
        buildPath(node, "", dictionary);
        return dictionary;
    }

    private void buildPath(Node node, String path, HashMap<String, String> dictionary) {
        if (node.getLeft() == null && node.getRight() == null) {
            dictionary.put(node.getKey(), path);
        } else {
            buildPath(node.getLeft(), path + "0", dictionary);
            buildPath(node.getRight(), path + "1", dictionary);
        }
    }

    public List<Integer> compress() {
        List<Integer> ints = new ArrayList<>();
        Map<String, String> dictionary = buildDictionary();
        int bitCounter = 0;
        int coder = 0;
        for (char c : getText().toCharArray()) {
            String key = dictionary.get(String.valueOf(c));
            for (char againC : key.toCharArray()) {
                if (bitCounter == 31) {
                    bitCounter = 0;
                    ints.add(coder);
                    coder = 0;
                }
                if (againC == '0') {
                    coder <<= 1;
                } else {
                    coder <<= 1;
                    coder++;
                }
                bitCounter++;
            }
        }
        ints.add(coder);
        ints.add(bitCounter);
        return ints;
    }

    //    public void compressToFile(){
//        var destination="compressed.hf";
//        try {
//            FileWriter fileWriter = new FileWriter(destination);
//            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(destination));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public String intToBinary(int number, int length) {
        char[] chars = new char[31];
        for (int j = length - 1; j > -1; j--) {
            chars[j] = String.valueOf(number % 2).charAt(0);
            number >>= 1;
        }
        return String.valueOf(chars);
    }

    public String toBinaryText(List<Integer> integers) {
        String answer = "";
        int lastNumberCounter = integers.remove(integers.size() - 1);
        int lastNumber = integers.remove(integers.size() - 1);
        System.out.println("integers = " + integers.size());
        int integer;
        for (int i = 0; i < integers.size(); i++) {
            integer = integers.get(i);
            answer += intToBinary(integer, 31);
        }

        answer += intToBinary(lastNumber, lastNumberCounter);
        return answer;
    }

    public String compressToConsole() {
        List<Integer> compressUtil = compress();
        String answer = toBinaryText(compressUtil);
        return answer;
    }

    public char getCharacter(Node node, String path) {
        char[] charPath = path.toCharArray();
        for (int i = 0; i < charPath.length; i++) {
            if (charPath[i] == '0') {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }
        return node.getKey().charAt(0);
    }

    public String decompress(String compressedText) {
        String decompressed = "";
        String tmp = "";
        Map<String, String> dictionary = buildDictionary();
        Node tree = buildHuffmanTree();
        for (int i = 0; i < compressedText.toCharArray().length; i++) {
            tmp += compressedText.charAt(i);

            if (dictionary.containsValue(tmp)) {
                decompressed += getCharacter(tree, tmp);
                tmp = "";
            }
        }
        return decompressed;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
