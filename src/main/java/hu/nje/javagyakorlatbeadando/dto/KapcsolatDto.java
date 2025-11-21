package hu.nje.javagyakorlatbeadando.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class KapcsolatDto {
    @NotBlank(message = "A név megadása kötelező")
    private String name;

    @NotBlank(message = "Az email megadása kötelező")
    @Email(message = "Érvénytelen email formátum")
    private String email;

    private String phone; // Opcionális

    @NotBlank(message = "A tárgy megadása kötelező")
    private String subject;

    @NotBlank(message = "Az üzenet nem lehet üres")
    @Size(min = 10, max = 65535, message = "Az üzenet 10 és 65535 karakter között lehet")
    private String message;
}
