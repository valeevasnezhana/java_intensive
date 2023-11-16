package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll(int page, int size) {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = createStatement(page, size,
                     connection);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                User user = extractUserFromResultSet(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    private User extractUserFromResultSet(ResultSet resultSet)
            throws SQLException {
        long userId = resultSet.getLong("user_id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");

        List<Chatroom> createdChatrooms = convertToChatrooms(
                resultSet.getString("created_chatroom_ids_names"));
        List<Chatroom> socializedChatrooms = convertToChatrooms(
                resultSet.getString("socialized_chatroom_ids_names"));

        User user = new User(userId, login, password);
        user.setCreatedRooms(createdChatrooms);
        user.setSocializedChatrooms(socializedChatrooms);
        return user;
    }

    private PreparedStatement createStatement(int page, int size,
                                              Connection connection)
            throws SQLException {
        String sql =
                "WITH user_created_chatrooms AS (" + " SELECT " + "DISTINCT " +
                        "    u.id AS user_id, " + "    u.login, " +
                        "    u.password, " +
                        "    STRING_AGG(DISTINCT CONCAT(c.id::text, ': ', c.name), ', ') " +
                        "AS chatroom_ids_names " + "FROM users u " +
                        "LEFT JOIN chatrooms c ON u.id = c.owner_id " +
                        "GROUP BY u.id, u.login " +
                        "ORDER BY u.id), user_socialized_chatrooms AS (" +
                        " SELECT DISTINCT " + "    u.id AS user_id, " +
                        "    STRING_AGG(DISTINCT CONCAT(m.room_id::text, ': ', c.name), ', ') " +
                        "AS room_ids_names " + "FROM users u " +
                        "LEFT JOIN messages m ON u.id = m.author_id " +
                        "LEFT JOIN chatrooms c ON m.room_id = c.id " +
                        "GROUP BY u.id ) " + "SELECT " + "    ucc.user_id, " +
                        "    ucc.login, " + "    ucc.password, " +
                        "    ucc.chatroom_ids_names AS created_chatroom_ids_names, " +
                        "    usc.room_ids_names AS socialized_chatroom_ids_names " +
                        "FROM user_created_chatrooms ucc " +
                        "JOIN user_socialized_chatrooms usc ON ucc.user_id = usc.user_id " +
                        "WHERE usc.user_id BETWEEN ? AND ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, page * size + 1);
        preparedStatement.setInt(2, (page + 1) * size);
        return preparedStatement;
    }

    private List<Chatroom> convertToChatrooms(String aggregatedChatrooms) {
        try {
            return Arrays.stream(aggregatedChatrooms.split(", ")).map(item -> {
                String[] roomDetails = item.split(": ");
                return new Chatroom(Long.parseLong(roomDetails[0]), roomDetails[1]);
            }).collect(Collectors.toList());
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
}
