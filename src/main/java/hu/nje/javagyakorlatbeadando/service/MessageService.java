package hu.nje.javagyakorlatbeadando.service;

import hu.nje.javagyakorlatbeadando.entity.Message;
import hu.nje.javagyakorlatbeadando.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}

