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


        User author = new User(4L, "user", "user");
        Chatroom room = new Chatroom(5L, "room");
        Message message = new Message(null, author, room, "Mya!",
                LocalDateTime.now());

        try {
            messagesRepository.save(message);
            System.out.println("Message saved with ID: " + message.getId());
        } catch (NotSavedSubEntityException e) {
            System.err.println("Error: " + e.getMessage());
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