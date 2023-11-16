INSERT INTO users (login, password) VALUES
    ('user1', 'password1'),
    ('user2', 'password2'),
    ('user3', 'password3'),
    ('user4', 'password4'),
    ('user5', 'password5'),
    ('user6', 'password6'),
    ('user6', 'password7');

INSERT INTO chatrooms (name, owner_id) VALUES
    ('Room1', 1),
    ('Room2', 2),
    ('Room3', 3),
    ('Room4', 4),
    ('Room5', 5);

INSERT INTO messages (author_id, room_id, text, date_time) VALUES
    (1, 1, 'Hello Room1!', CURRENT_TIMESTAMP),
    (2, 1, 'Hi, user1!', CURRENT_TIMESTAMP),
    (3, 2, 'Chatting in Room2.', CURRENT_TIMESTAMP),
    (4, 3, 'Greetings from Room3!', CURRENT_TIMESTAMP),
    (6, 3, 'Krya!', CURRENT_TIMESTAMP),
    (5, 4, 'Room4 message.', CURRENT_TIMESTAMP);
