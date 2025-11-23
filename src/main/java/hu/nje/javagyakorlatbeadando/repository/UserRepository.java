package hu.nje.javagyakorlatbeadando.repository;

import hu.nje.javagyakorlatbeadando.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email); // Email alapján lesz a bejelentkezés
    Optional<User> findByUsername(String username);
}
