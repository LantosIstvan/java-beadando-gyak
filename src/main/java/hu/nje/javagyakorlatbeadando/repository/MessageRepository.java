package hu.nje.javagyakorlatbeadando.repository;

import hu.nje.javagyakorlatbeadando.entity.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findByUserEmail(String email);
}
