CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE chatrooms (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    owner_id BIGINT REFERENCES users(id)
);

CREATE TABLE messages (
    id SERIAL PRIMARY KEY,
    author_id BIGINT REFERENCES users(id),
    room_id BIGINT REFERENCES chatrooms(id),
    text VARCHAR(1000) NOT NULL,
    date_time TIMESTAMP NOT NULL
);



