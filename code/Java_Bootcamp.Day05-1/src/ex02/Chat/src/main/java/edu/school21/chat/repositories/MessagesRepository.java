package edu.school21.chat.repositories;

import java.util.Optional;
import edu.school21.chat.models.Message;

public interface MessagesRepository {
    Optional<Message> findById(Long id);
    void save(Message message);
}