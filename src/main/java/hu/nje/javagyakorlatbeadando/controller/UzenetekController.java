package hu.nje.javagyakorlatbeadando.controller;

import hu.nje.javagyakorlatbeadando.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UzenetekController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/uzenetek")
    public String getUzenetek(Model model) {
        model.addAttribute("uzenetek", messageRepository.findAll());
        model.addAttribute("pageTitle", "Beérkezett üzenetek");
        return "uzenetek";
    }

}
