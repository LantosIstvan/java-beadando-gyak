package hu.nje.javagyakorlatbeadando.controller;

import hu.nje.javagyakorlatbeadando.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String deleteUser(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            userRepository.delete(userRepository.findById(id).get());
            redirectAttributes.addFlashAttribute("success", "Felhasználó sikeresen törölve.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Hiba történt a felhasználó törlése során.");
            e.printStackTrace();
        }
        return "redirect:/admin";
    }

}
