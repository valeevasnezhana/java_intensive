package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        DataSource dataSource = configureDataSource();

        MessagesRepository messagesRepository =
                new MessagesRepositoryJdbcImpl(dataSource);


        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a message ID");
        Long messageId = scanner.nextLong();

        Optional<Message> messageOptional = messagesRepository.findById(messageId);

        messageOptional.ifPresent(System.out::println);
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