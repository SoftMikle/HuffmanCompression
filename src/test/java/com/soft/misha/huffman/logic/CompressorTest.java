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

    }

    @Test
    void buildHuffmanTreeVersion1() {
        Compressor compressor = new Compressor("abcdabcabbbbafghjklbbbbbbbbb");
        compressor.compress();
        System.out.println(compressor.getText());
        compressor.dictionary.forEach((k, v) -> System.out.println(k + "\t" + v));
        compressor.writeToFile();
        Decompressor decompressor = new Decompressor();
        decompressor.readFromFile("myfile.mf");
        decompressor.toConsole();
    }
}