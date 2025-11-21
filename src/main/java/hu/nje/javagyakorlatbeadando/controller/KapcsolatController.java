package hu.nje.javagyakorlatbeadando.controller;

import hu.nje.javagyakorlatbeadando.dto.KapcsolatDto;
import hu.nje.javagyakorlatbeadando.entity.Message;
import hu.nje.javagyakorlatbeadando.repository.MessageRepository;
import hu.nje.javagyakorlatbeadando.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class KapcsolatController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/kapcsolat")
    public String getKapcsolat(Model model) {
        model.addAttribute("kapcsolat_form_params", new KapcsolatDto());
        return "kapcsolat";
    }

    @PostMapping("/kapcsolat")
    public String postKapcsolat(
        @Valid @ModelAttribute("kapcsolat_form_params") KapcsolatDto kapcsolatDto,
        BindingResult bindingResult,
        RedirectAttributes redirectAttributes,
        Principal principal
    ) {
        if (bindingResult.hasErrors()) return "kapcsolat";

        Message message = new Message();
        message.setName(kapcsolatDto.getName());
        message.setEmail(kapcsolatDto.getEmail());
        message.setPhone(kapcsolatDto.getPhone());
        message.setSubject(kapcsolatDto.getSubject());
        message.setMessage(kapcsolatDto.getMessage());

        if (principal != null) {
            userRepository.findByEmail(principal.getName()).ifPresent(user -> {
                message.setUser(user);
                message.setName(user.getUsername());
                message.setEmail(user.getEmail());
            });
        }

        messageRepository.save(message);
        redirectAttributes.addFlashAttribute("success_msg", "Sikeres üzenetküldés!");
        return "redirect:/kapcsolat";
    }

}
