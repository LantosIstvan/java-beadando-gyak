package hu.nje.javagyakorlatbeadando.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    /**
     * Ez a metódus minden egyes Controller hívás előtt lefut,
     * és beteszi a modellbe a "currentURI" változót.
     */
    @ModelAttribute("currentURI")
    public String currentURI(HttpServletRequest request) {
        return request.getRequestURI();
    }
}
