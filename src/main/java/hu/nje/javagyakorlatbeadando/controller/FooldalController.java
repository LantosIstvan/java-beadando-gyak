package hu.nje.javagyakorlatbeadando.controller;

import hu.nje.javagyakorlatbeadando.dto.KapcsolatDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FooldalController {

    @GetMapping("/")
    public String getFooldal(Model model) {
        model.addAttribute("kapcsolat_form_params", new KapcsolatDto());
        // model.addAttribute("products", productList); // TODO
        return "fooldal_get";
    }

}
