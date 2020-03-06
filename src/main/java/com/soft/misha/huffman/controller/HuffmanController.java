package com.soft.misha.huffman.controller;

import com.soft.misha.huffman.data.ResultData;
import com.soft.misha.huffman.logic.Runner;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class HuffmanController {

    private final Runner runner;

    @GetMapping
    public String init() {
        return "huffman";
    }

    @PostMapping(path="/")
    public String run(@ModelAttribute("text") String text, RedirectAttributes redirectAttributes) {
        ResultData data = runner.run(text);
        redirectAttributes.addFlashAttribute("res", data);
        return "redirect:/";
    }
}
