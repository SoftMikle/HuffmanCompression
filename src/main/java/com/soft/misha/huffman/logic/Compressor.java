package com.soft.misha.huffman.logic;

import com.soft.misha.huffman.data.Node;

import java.io.*;
import java.util.*;


public class Compressor {
    private String text;
    String binaryText;
    PriorityQueue<Node> occurrences;
    Node tree;
    Map<String, String> dictionary;
    List<Integer> compressedTextInInts;
    String compressedText;

    public Compressor(String text) {
        this.text = text;
    }

    private Map<Character, Integer> calcOccurrence() {
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

    private PriorityQueue<Node> calcOccurrenceNode() {
        var nodes = new PriorityQueue<>(Comparator.comparingInt(Node::getValue));
        calcOccurrence().forEach((key, value) -> nodes.add(new Node(key.toString(), value)));
        return nodes;
    }

    private Node buildHuffmanTree() {
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

    private Map<String, String> buildDictionary() {
        HashMap<String, String> dictionary = new HashMap<>();
        buildPath(tree, "", dictionary);
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
        occurrences = calcOccurrenceNode();
        tree = buildHuffmanTree();
        dictionary = buildDictionary();

        List<Integer> buckets = new ArrayList<>();
        int bitCounter = 0;
        int coder = 0;
        for (char c : text.toCharArray()) {
            String key = dictionary.get(String.valueOf(c));
            for (char againC : key.toCharArray()) {
                if (bitCounter == 31) {
                    bitCounter = 0;
                    buckets.add(coder);
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
        buckets.add(coder);
        buckets.add(bitCounter);
        compressedTextInInts = buckets;
        compressedText = intsToBinaryText();
        return compressedTextInInts;
    }

    public void writeToFile() {

        try (FileWriter fileWriter = new FileWriter("myfile.mf")){
            DataOutputStream dos = new DataOutputStream(new FileOutputStream("myfile.mf"));
            dos.writeInt(compressedTextInInts.size());
            for (int i = 0; i < compressedTextInInts.size(); i++) {
                dos.writeInt(compressedTextInInts.get(i));
            }
            dos.flush();
            ObjectOutputStream ous = new ObjectOutputStream(dos);
            ous.writeObject(dictionary);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String intToBinary(int number, int length) {
        char[] chars = new char[31];
        for (int j = length - 1; j > -1; j--) {
            chars[j] = String.valueOf(number % 2).charAt(0);
            number >>= 1;
        }
        return String.valueOf(chars);
    }

    public String intsToBinaryText() {
        StringBuffer answer = new StringBuffer();
        int lastNumberCounter = compressedTextInInts.get(compressedTextInInts.size() - 1);
        int lastNumber = compressedTextInInts.get(compressedTextInInts.size() - 2);
        int integer;
        for (int i = 0; i < compressedTextInInts.size() - 2; i++) {
            integer = compressedTextInInts.get(i);
            answer.append(intToBinary(integer, 31));
        }

        answer.append(intToBinary(lastNumber, lastNumberCounter));
        return answer.toString();
    }

//    public String compressToConsole() {
//        List<Integer> compressUtil = compress();
//        String answer = toBinaryText(compressUtil);
//        return answer;
//    }

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

    public String decompress() {
        String decompressed = "";
        StringBuilder tmp = new StringBuilder();
        Node tree = buildHuffmanTree();
        for (int i = 0; i < compressedText.toCharArray().length; i++) {
            tmp.append(compressedText.charAt(i));

            if (dictionary.containsValue(tmp.toString())) {
                decompressed += getCharacter(tree, tmp.toString());
                tmp = new StringBuilder();
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
