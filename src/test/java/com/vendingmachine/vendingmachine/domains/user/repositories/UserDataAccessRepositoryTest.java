package com.vendingmachine.vendingmachine.domains.user.repositories;

import com.vendingmachine.vendingmachine.domains.user.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserDataAccessRepositoryTest {
    @Mock
    UserRepository userRepository;

    private UserDataAccessRepository underTest;
    @BeforeEach
    void setUp() {
        underTest = new UserDataAccessRepository(userRepository);
    }

    @Test
    void getAllUsers() {
        underTest.getAllUsers();

        verify(userRepository).findAll();
    }

    @Test
    void addUser() {
        String username = "foo";
        User user = new User(username, "password", 10, "ROLE_ADMIN");

        underTest.addUser(user);
        verify(userRepository).save(user);
    }

    @Test
    void getUser() {
        int id = 1;
        underTest.getUser(id);
        verify(userRepository).findById(id);
    }

    @Test
    void existsUserWithUsername() {
        String username = "foo";
        underTest.existsUserWithUsername(username);
        verify(userRepository).existsUserByUsername(username);
    }

    @Test
    void updateUser() {
        String username = "foo";
        User user = new User(username, "password", 10, "ROLE_ADMIN");

        underTest.updateUser(user);
        verify(userRepository).save(user);
    }

    @Test
    void existsUserWithId() {
        int id = 1;
        underTest.existsUserWithId(id);
        verify(userRepository).existsUserById(id);
    }

    @Test
    void deleteUser() {
        int  id = 1;
        underTest.deleteUser(id);
        verify(userRepository).deleteById(id);
    }

    @Test
    void getUserByUsername() {
        String username = "foo";
        underTest.existsUserWithUsername(username);
        verify(userRepository).existsUserByUsername(username);
    }

    @Test
    void reset() {
        User user = new User("foo", "password", 10, "ROLE_ADMIN");
        underTest.reset(user);
        verify(userRepository).save(user);
    }
}