package com.soft.misha.huffman.logic;

import com.soft.misha.huffman.data.Node;
import com.soft.misha.huffman.data.ResultData;
import com.soft.misha.huffman.util.BinaryUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class Runner {

    public ResultData run(String text) {
        Compressor compressor = new Compressor(text);
        ResultData resultData = new ResultData();
        compressor.compress();
        resultData.setText(text);
        resultData.setBinaryText(BinaryUtil.convertToBinary(text));
        resultData.setDictionary(compressor.dictionary);
        resultData.setBinaryCompressText(compressor.compressedText);
        resultData.setOccurrences(compressor.occurrences);
        resultData.setResultText(compressor.decompress());
        return resultData;
    }

}
