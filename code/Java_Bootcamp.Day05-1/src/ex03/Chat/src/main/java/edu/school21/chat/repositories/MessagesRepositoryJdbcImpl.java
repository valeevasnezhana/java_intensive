package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
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

    @Override
    public void save(Message message) {
        try (Connection connection = dataSource.getConnection()) {
            if (message.getAuthor() == null) {
                throw new NotSavedSubEntityException("Message author is null");
            }

            if (message.getRoom() == null) {
                throw new NotSavedSubEntityException("Message room is null");
            }


            if (!userExists(connection, message.getAuthor().getId())) {
                throw new NotSavedSubEntityException(
                        "There is no user with " + "id=" +
                                message.getAuthor().getId());
            }

            if (!chatroomExists(connection, message.getRoom().getId())) {
                throw new NotSavedSubEntityException(
                        "There is no chatroom with " + "id=" +
                                message.getRoom().getId());
            }

            String messageInsertQuery =
                    "INSERT INTO messages (author_id, room_id, text, date_time)" +
                            " VALUES (?, ?, ?, ?) RETURNING id;";
            PreparedStatement messageInsertStatement =
                    connection.prepareStatement(messageInsertQuery);
            messageInsertStatement.setLong(1, message.getAuthor().getId());
            messageInsertStatement.setLong(2, message.getRoom().getId());
            messageInsertStatement.setString(3, message.getText());
            messageInsertStatement.setTimestamp(4,
                    Timestamp.valueOf(message.getDateTime()));
            ResultSet generatedKeys = messageInsertStatement.executeQuery();
            if (generatedKeys.next()) {
                Long messageId = generatedKeys.getLong(1);
                message.setId(messageId);
            } else {
                throw new SQLException(
                        "Failed to retrieve generated ID for the message.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Message message) {
        try (Connection connection = dataSource.getConnection()) {
            if (message == null) {
                throw new NotSavedSubEntityException(
                        "Message ID cannot be null for update.");
            }

            if (!messageExists(connection, message.getId())) {
                throw new NotSavedSubEntityException(
                        "Message with ID " + message.getId() +
                                " does not exist.");
            }

            StringBuilder updateQuery =
                    new StringBuilder("UPDATE messages SET ");
            boolean isFirst = true;

            if (message.getText() != null) {
                updateQuery.append("text = ?");
                isFirst = false;
            }

            if (message.getDateTime() != null) {
                if (!isFirst) {
                    updateQuery.append(", ");
                }
                updateQuery.append("date_time = ?");
            }

            updateQuery.append(" WHERE id = ?");
            PreparedStatement updateStatement =
                    connection.prepareStatement(updateQuery.toString());

            // Set parameters for the update query
            int parameterIndex = 1;

            if (message.getText() != null) {
                updateStatement.setString(parameterIndex++, message.getText());
            }

            if (message.getDateTime() != null) {
                updateStatement.setTimestamp(parameterIndex++,
                        Timestamp.valueOf(message.getDateTime()));
            }

            updateStatement.setLong(parameterIndex, message.getId());

            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean messageExists(Connection connection, Long messageId)
            throws SQLException {
        String query = "SELECT COUNT(*) FROM messages WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, messageId);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1) > 0;
    }

    private boolean userExists(Connection connection, Long userId)
            throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, userId);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1) > 0;
    }

    private boolean chatroomExists(Connection connection, Long chatroomId)
            throws SQLException {
        String query = "SELECT COUNT(*) FROM chatrooms WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, chatroomId);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1) > 0;
    }
}

