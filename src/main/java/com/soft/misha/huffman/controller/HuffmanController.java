package ua.com.alevel.binary.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.com.alevel.binary.service.HuffmanService;

@Controller
@AllArgsConstructor
public class HuffmanController {

    private final HuffmanService huffmanService;

    @GetMapping
    public String init() {
        return "huffman";
    }

    @PostMapping
    public String run(@ModelAttribute("text") String text, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("res", huffmanService.run(text));
        return "redirect:/";
    }
}
