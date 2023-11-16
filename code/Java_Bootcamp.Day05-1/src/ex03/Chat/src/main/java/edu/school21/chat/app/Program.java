package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import edu.school21.chat.repositories.NotSavedSubEntityException;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        DataSource dataSource = configureDataSource();

        MessagesRepository messagesRepository =
                new MessagesRepositoryJdbcImpl(dataSource);


        Optional<Message> messageOptional = messagesRepository.findById(2L);

        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setText("Bye");
            message.setDateTime(null);

            messagesRepository.update(message);
            System.out.println("Message with ID " + message.getId() + " updated.");
        } else {
            System.out.println("Message with ID 11 not found.");
        }
    }

    private static DataSource configureDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://127.0.0.1/postgres");
        config.setUsername("postgres");
        config.setPassword("1234");
        config.setMaximumPoolSize(10);

        return new HikariDataSource(config);
    }
}