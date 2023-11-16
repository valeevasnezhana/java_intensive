package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT m.id, m.text, m.date_time, " +
                    "u.id AS author_id, u.login AS author_login, " +
                    "u.password AS author_password, " +
                    "c.id AS room_id, c.name AS room_name " +
                    "FROM messages m " + "JOIN users u ON m.author_id = u.id " +
                    "JOIN chatrooms c ON m.room_id = c.id " + "WHERE m.id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User author = new User(resultSet.getLong("author_id"),
                        resultSet.getString("author_login"),
                        resultSet.getString("author_password"));

                Chatroom room = new Chatroom(resultSet.getLong("room_id"),
                        resultSet.getString("room_name"));

                Message message =
                        new Message(resultSet.getLong("id"), author, room,
                                resultSet.getString("text"),
                                resultSet.getTimestamp("date_time")
                                        .toLocalDateTime());

                return Optional.of(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}

