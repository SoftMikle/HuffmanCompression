package com.soft.misha.huffman.logic;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Decompressor {

    Map<String, String> dictionary;
    List<Integer> compressedTextContainers = new ArrayList<>();
    String compressedText;

    public void readFromFile(String path) {
        int length;

        try (FileReader fileReader = new FileReader(path)) {
            DataInputStream dos = new DataInputStream(new FileInputStream(path));
            length = dos.readInt();
            for (int i = 0; i < length; i++) {
                compressedTextContainers.add(dos.readInt());
            }
            ObjectInputStream ois = new ObjectInputStream(dos);
            dictionary = (Map<String, String>) ois.readObject();

            dictionary = dictionary.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void toConsole() {
        System.out.println("dictionary = \n" + dictionary);
        System.out.println("Decompressed text = " + decompress());
    }

    public String toBinaryText() {
        String answer = "";
        int lastNumberCounter = compressedTextContainers.remove(compressedTextContainers.size() - 1);
        int lastNumber = compressedTextContainers.remove(compressedTextContainers.size() - 1);
        System.out.println("integers = " + compressedTextContainers.size());
        int integer;
        for (int i = 0; i < compressedTextContainers.size(); i++) {
            integer = compressedTextContainers.get(i);
            answer += intToBinary(integer, 31);
        }

        answer += intToBinary(lastNumber, lastNumberCounter);
        return answer;
    }

    public String intToBinary(int number, int length) {
        char[] chars = new char[31];
        for (int j = length - 1; j > -1; j--) {
            chars[j] = String.valueOf(number % 2).charAt(0);
            number >>= 1;
        }
        return String.valueOf(chars);
    }

    public String decompress() {
        compressedText = toBinaryText();
        System.out.println("compressedText = " + compressedText);
        StringBuilder decompressed = new StringBuilder();
        String tmp = "";
        for (int i = 0; i < compressedText.toCharArray().length; i++) {
            tmp += compressedText.charAt(i);
            String tmpValue = dictionary.get(tmp);
            if (tmpValue != null) {
                decompressed.append(tmpValue);
                tmp = "";
            }
        }
        return decompressed.toString();
    }
}
