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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
