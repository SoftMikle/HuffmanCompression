package com.soft.misha.huffman.logic;

import com.soft.misha.huffman.data.ResultData;
import com.soft.misha.huffman.util.BinaryUtil;
import org.junit.jupiter.api.Test;

class CompressorTest {


    @Test
    void calcOccurrence() {
    }

    @Test
    void buildHuffmanTree() {

    }

    @Test
    void buildHuffmanTreeVersion1() {
        Compressor compressor = new Compressor("qwe");
        compressor.compress();
        System.out.println(compressor.getText());
        compressor.dictionary.forEach((k, v) -> System.out.println(k + "\t" + v));
        compressor.writeToFile();
//        Decompressor decompressor = new Decompressor();
//        decompressor.readFromFile("myfile.mf");
//        decompressor.toConsole();
        System.out.println(compressor.decompress());
    }
}