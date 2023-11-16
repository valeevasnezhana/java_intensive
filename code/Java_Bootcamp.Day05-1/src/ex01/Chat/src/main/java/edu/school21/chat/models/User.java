package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
    private Long id;
    private final String login;
    private final String password;
    private List<Chatroom> createdRooms;
    private List<Chatroom> socializedChatrooms;

    public User(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }


    public List<Chatroom> getCreatedRooms() {
        return createdRooms;
    }

    public void setCreatedRooms(List<Chatroom> createdRooms) {
        this.createdRooms = createdRooms;
    }

    public List<Chatroom> getSocializedChatrooms() {
        return socializedChatrooms;
    }

    public void setSocializedChatrooms(List<Chatroom> socializedChatrooms) {
        this.socializedChatrooms = socializedChatrooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", login=\"" + login + '\"' +
                ", password=\"" + password + '\"' +
                ", createdRooms=" + createdRooms +
                ", rooms=" + socializedChatrooms +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long authorId) {
        id = authorId;
    }

}