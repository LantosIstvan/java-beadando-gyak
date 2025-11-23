package hu.nje.javagyakorlatbeadando.service;

import hu.nje.javagyakorlatbeadando.entity.User;
import hu.nje.javagyakorlatbeadando.repository.UserRepository;
import hu.nje.javagyakorlatbeadando.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Dependency injection

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // user-t most email cím alapján azonosítjuk
        User user = userRepository.findByEmail(userName)
            .orElseThrow(() -> new UsernameNotFoundException("Email " + userName + " not found"));

        // Egy új User-t (Security) hoz létre. Ez nem az a User osztály, amit mi hoztunk létre,
        // hanem a spring security saját User osztálya:
        // https://docs.spring.io/spring-security/site/docs/4.0.x/apidocs/org/springframework/security/core/userdetails/User.html
        // Ez a User osztály implementálja a UserDetails interfészt (olvasható az oldalon is)

        // Saját CustomUserDetails visszaadása, ami tartalmazza a teljes nevet is
        return new CustomUserDetails(user.getEmail(), user.getPassword(), getAuthorities(user), user.getUsername());
    }

    // A kiválasztott felhasználó szerepeinek lekérdezése:.
    // A szerepek is olyan tulajdonsága a felhasználónak, mint a neve, címe, telefonszáma, …
    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(user.getRole());
        return authorities;
    }

}
