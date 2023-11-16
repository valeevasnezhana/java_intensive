package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Message {
    private Long id;
    private final User author;
    private final Chatroom room;
    private String text;
    private LocalDateTime dateTime;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

    public Message(Long id, User author, Chatroom room, String text,
                   LocalDateTime dateTime) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id.equals(message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Message : {\n" + "  id=" + id +
                ",\n  author=" + author +
                ",\n  room=" + room +
                ",\n  text=\"" + text + '\"' +
                ",\n  dateTime=" +
                dateTime.format(FORMATTER) + "\n}";
    }

    public void setId(Long messageId) {
        id = messageId;
    }

    public void setDateTime(LocalDateTime newDateTime) {
        dateTime = newDateTime;
    }
}

