package edu.school21.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class User {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(User.class);
    private Long identifier;
    private String login;
    private String password;
    private boolean isAuthenticated;

    public User(Long identifier, String login, String password, boolean isAuthenticated) {
        this.identifier = identifier;
        this.login = login;
        this.password = password;
        this.isAuthenticated = isAuthenticated;
        LOGGER.debug("New User object: id = {}, login = {}, password = [SENSITIVE], isAuthenticated = {}", identifier, login, isAuthenticated);
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        LOGGER.debug("Setting identifier for User {}. New value = {}", this.login, identifier);
        this.identifier = identifier;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        LOGGER.debug("Setting login for User {}. New value = {}", this.login, login);
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        LOGGER.debug("Setting password for User {}. New value = [SENSITIVE]", this.login);
        this.password = password;
    }

    public boolean isAuthenticated() {
        LOGGER.debug("Checking isAuthenticated for User {}. Value = {}", this.login, isAuthenticated);
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        LOGGER.debug("Setting authenticated for User {}. New value = {}", this.login, authenticated);
        isAuthenticated = authenticated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isAuthenticated == user.isAuthenticated &&
                Objects.equals(identifier, user.identifier) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, login, password, isAuthenticated);
    }

    @Override
    public String toString() {
        return "User{" +
                "identifier=" + identifier +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", isAuthenticated=" + isAuthenticated +
                '}';
    }
}
