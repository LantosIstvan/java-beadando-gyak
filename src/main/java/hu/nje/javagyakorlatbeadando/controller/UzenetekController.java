package hu.nje.javagyakorlatbeadando.controller;

import hu.nje.javagyakorlatbeadando.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UzenetekController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/uzenetek")
    public String uzenetekPage(Model model) {
        model.addAttribute("uzenetek", messageService.getAllMessages());
        model.addAttribute("pageTitle", "Beérkezett üzenetek");
        return "uzenetek";
    }
}

