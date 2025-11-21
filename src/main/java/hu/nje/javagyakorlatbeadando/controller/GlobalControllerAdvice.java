package hu.nje.javagyakorlatbeadando.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    /**
     * Ez a metódus biztosítja, hogy ha van űrlap az oldalon,
     * a session és a CSRF token időben létrejöjjön.
     */
    @ModelAttribute
    public void forceSessionForCsrf(HttpServletRequest request) {
        // Session létrehozása a CSRF hiba elkerülésére
        HttpSession session = request.getSession(true);
    }

}
