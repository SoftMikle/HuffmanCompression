package com.soft.misha.huffman.compress;

import com.soft.misha.huffman.data.Node;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CompressorTest {


    @Test
    void calcOccurrence() {
    }

    @Test
    void buildHuffmanTree() {
        Compressor compressor = new Compressor();
        compressor.setText("abcdabcabbbba");
        Node tree = compressor.buildHuffmanTree();
        Map<String, String> dictionary = compressor.buildDictionary();
        List<Integer> compress = compressor.compress();
        dictionary.forEach((k, v) -> System.out.println(k + "\t" + v));
        compressor.compressToConsole();
    }

    @Test
    void buildHuffmanTreeVersion1() {
        Compressor compressor = new Compressor();
        compressor.setText("abcdabcabbbbafghjklbbbbbbbbb");
        Node tree = compressor.buildHuffmanTree();
        Map<String, String> dictionary = compressor.buildDictionary();
        dictionary.forEach((k, v) -> System.out.println(k + "\t" + v));
        String compressedText = compressor.compressToConsole();
        System.out.println(compressedText);
        System.out.println("\n");
        System.out.println("Decompressed text  = " + compressor.decompress(compressedText));
        System.out.println("Those u entered is = " + compressor.getText());
    }
}