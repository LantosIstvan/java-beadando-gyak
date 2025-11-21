package hu.nje.javagyakorlatbeadando.repository;

import hu.nje.javagyakorlatbeadando.entity.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
