package hu.nje.javagyakorlatbeadando.controller;

import hu.nje.javagyakorlatbeadando.entity.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KapcsolatController {

    @GetMapping("/kapcsolat")
    public String getKapcsolat(Model model) {
        model.addAttribute("kapcsolat_form_params", new Message());
        return "kapcsolat_get";
    }

}
