package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsersServiceImpl {

    private final UsersRepository usersRepository;

    private static final Logger LOGGER =
            LoggerFactory.getLogger(UsersServiceImpl.class);

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        LOGGER.debug("UsersServiceImpl initialized");
    }

    public boolean authenticate(String login, String password)
            throws EntityNotFoundException, AlreadyAuthenticatedException {
        LOGGER.debug("Trying to authenticate user with login {}", login);
        User user = usersRepository.findByLogin(login);
        if (user == null) {
            LOGGER.debug("User not found");
            throw new EntityNotFoundException("User not found");
        } else if (user.isAuthenticated()) {
            LOGGER.debug("This user is already authenticated");
            throw new AlreadyAuthenticatedException(
                    "User already authenticated");
        } else {
            if (user.getPassword().equals(password)) {
                user.setAuthenticated(true);
                usersRepository.update(user);
                LOGGER.debug("User with login {} has been successfully " +
                        "authenticated", login);
                return true;
            } else {
                LOGGER.debug("Authentication failed - wrong password for user" +
                        " {}", login);
                return false;
            }
        }
    }
}
