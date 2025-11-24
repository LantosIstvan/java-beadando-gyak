package hu.nje.javagyakorlatbeadando.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //http.csrf(csrf -> csrf.disable())
        http
            .csrf(csrf -> csrf.ignoringRequestMatchers("/restful/**"))
            .authorizeHttpRequests(
                auth -> auth
                    // Csak be nem jelentkezett felhasználók érhetik el a login és register oldalakat
                    .requestMatchers("/login", "/register").anonymous()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/uzenetek").authenticated()
                    .requestMatchers("/restful/**").permitAll()
                    .anyRequest().permitAll() // Alap esetben engedélyezünk mindent, majd később korlátozunk
            )
            .formLogin(
                form -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                    .permitAll()
            ).logout(
                logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .permitAll()
            )
            .exceptionHandling(exception -> exception
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.sendRedirect("/");
                })
            );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
