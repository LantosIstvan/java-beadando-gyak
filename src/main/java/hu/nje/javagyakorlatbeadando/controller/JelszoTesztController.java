package hu.nje.javagyakorlatbeadando.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JelszoTesztController {
    @GetMapping("/jelszoteszt")
    public List<String> getJelszoTeszt() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return Arrays.asList(
            bCryptPasswordEncoder.encode("Admin"),
            bCryptPasswordEncoder.encode("User1")
        );
    }
}
