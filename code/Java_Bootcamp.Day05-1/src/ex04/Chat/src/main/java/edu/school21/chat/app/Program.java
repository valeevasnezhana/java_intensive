package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.*;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        DataSource dataSource = configureDataSource();

        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(dataSource);

        int page = 1;
        int size = 4;

        List<User> users = usersRepository.findAll(page, size);

        for (User user : users) {
            System.out.println(user);
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