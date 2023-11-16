package edu.school21.services;

import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsersServiceImplTest {

    UsersRepository mockedUsersRepository = mock(UsersRepository.class);
    UsersServiceImpl usersService = new UsersServiceImpl(mockedUsersRepository);

    private static final Logger LOGGER =
            LoggerFactory.getLogger(UsersServiceImplTest.class);

    @Test
    void testCorrectLoginPassword() {
        LOGGER.info("Initiating testCorrectLoginPassword test...");
        User user = new User(1L, "Login", "Password", false);
        when(mockedUsersRepository.findByLogin("Login")).thenReturn(user);
        assertTrue(usersService.authenticate("Login", "Password"));
        verify(mockedUsersRepository, times(1)).update(user);
        assertTrue(user.isAuthenticated());
        LOGGER.info(
                "testCorrectLoginPassword successfully " +
                        "passed\n_______________________________________________");
    }

    @Test
    void testIncorrectLogin() {
        LOGGER.info("Initiating testIncorrectLogin test...");
        when(mockedUsersRepository.findByLogin("Login")).thenReturn(null);
        assertThrows(EntityNotFoundException.class,
                () -> usersService.authenticate("Login", "Password"));
        LOGGER.info(
                "testIncorrectLogin successfully " +
                        "passed\n_______________________________________________");
    }

    @Test
    void testIncorrectPassword() {
        LOGGER.info("Initiating testIncorrectPassword test...");
        User user = new User(1L, "Login", "Password", false);
        when(mockedUsersRepository.findByLogin("Login")).thenReturn(user);
        assertFalse(usersService.authenticate("Login", "WrongPassword"));
        assertFalse(user.isAuthenticated());
        LOGGER.info("testIncorrectPassword successfully " +
                "passed\n_______________________________________________");
    }
}
