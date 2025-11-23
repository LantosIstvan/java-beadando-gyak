package hu.nje.javagyakorlatbeadando.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestfulPageController {

    @GetMapping("/restful")
    public String loadRestfulPage() {
        // Ez a templates/restful.html fájlt tölti be,
        // ami majd örökli a layout-ot.
        return "restful";
    }
}