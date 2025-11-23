package hu.nje.javagyakorlatbeadando.controller;

import hu.nje.javagyakorlatbeadando.entity.User;
import hu.nje.javagyakorlatbeadando.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin";
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam("id") Long id, RedirectAttributes redirectAttributes, Principal principal) {
        try {
            Optional<User> userToDeleteOptional = userRepository.findById(id);
            if (userToDeleteOptional.isPresent()) {
                User userToDelete = userToDeleteOptional.get();
                // A CustomUserDetailsService alapján a bejelentkezés email címmel történik
                if (principal != null && principal.getName().equals(userToDelete.getEmail())) {
                    redirectAttributes.addFlashAttribute("error", "Saját magadat nem törölheted!");
                    return "redirect:/admin";
                }
                userRepository.delete(userToDelete);
                redirectAttributes.addFlashAttribute("success", "Felhasználó sikeresen törölve.");
            } else {
                redirectAttributes.addFlashAttribute("error", "A felhasználó nem található.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Hiba történt a felhasználó törlése során.");
            e.printStackTrace();
        }
        return "redirect:/admin";
    }

}
