package hu.nje.javagyakorlatbeadando.controller;

import hu.nje.javagyakorlatbeadando.repository.MessageRepository;
import hu.nje.javagyakorlatbeadando.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UzenetekController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    // Az Authentication objektumot használjuk a Principal helyett,
    // mert szükségünk van a getAuthorities() metódusra a jogosultságok (ROLE_ADMIN) ellenőrzéséhez.
    @GetMapping("/uzenetek")
    public String getUzenetek(Model model, Authentication authentication) {
        // Ha felhasználó ADMIN role-al rendelkezik, akkor mindenki üzenetét láthatja.
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            model.addAttribute("uzenetek", messageRepository.findAllByOrderByIdDesc());
        } else {
            model.addAttribute("uzenetek", messageRepository.findByUserEmailOrderByIdDesc(authentication.getName()));
        }
        return "uzenetek";
    }

}
