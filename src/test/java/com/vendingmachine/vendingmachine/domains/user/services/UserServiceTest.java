package com.vendingmachine.vendingmachine.domains.user.services;

import com.vendingmachine.vendingmachine.domains.user.daos.UserDao;
import com.vendingmachine.vendingmachine.domains.user.dtos.UserDTO;
import com.vendingmachine.vendingmachine.domains.user.dtos.UserDTOMapper;
import com.vendingmachine.vendingmachine.domains.user.entities.User;
import com.vendingmachine.vendingmachine.domains.user.resources.UserRegistrationRequest;
import com.vendingmachine.vendingmachine.domains.user.resources.UserUpdateRequest;
import com.vendingmachine.vendingmachine.exception.DuplicateResourceException;
import com.vendingmachine.vendingmachine.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao userDao;
    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService underTest;
    private final UserDTOMapper userDTOMapper = new UserDTOMapper();

    @BeforeEach
    void setup() {
        underTest = new UserService(userDao, userDTOMapper, passwordEncoder);
    }

    @Test
    void getAllUsers() {
        underTest.getUsers();
        verify(userDao).getAllUsers();
    }

    @Test
    void addUser() {
        String username = "foo";
        when(userDao.existsUserWithUsername(username)).thenReturn(false);

        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest(username, "password", 10, "ROLE_ADMIN");
        String passwordHash = "adscasdav4343434";
        when(passwordEncoder.encode(userRegistrationRequest.password())).thenReturn(passwordHash);

        underTest.addUser(userRegistrationRequest);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao).addUser(userArgumentCaptor.capture());

        User userCapture = userArgumentCaptor.getValue();

        assertThat(userCapture.getId()).isNull();
        assertThat(userCapture.getUsername()).isEqualTo(userRegistrationRequest.username());
        assertThat(userCapture.getPassword()).isEqualTo(passwordHash);
        assertThat(userCapture.getDeposit()).isEqualTo(userRegistrationRequest.deposit());
    }

    @Test
    void willThrowWhenUsernameExistsWhileAddingUser() {
        String username = "foo";
        when(userDao.existsUserWithUsername(username)).thenReturn(true);

        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest(username, "password", 10, "ROLE_ADMIN");
        assertThatThrownBy(()->underTest.addUser(userRegistrationRequest)).isInstanceOf(DuplicateResourceException.class).hasMessage("username already taken");

        verify(userDao, never()).addUser(any());
    }


    @Test
    void getUser() {
        Integer id = 1;
        User user = new User(id, "foo", "password", 10, "ROLE_USER");

        when(userDao.getUser(id)).thenReturn(Optional.of(user));
        UserDTO expected = userDTOMapper.apply(user);
        UserDTO actual = underTest.getUser(id);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void willThrowWhenGetUserReturnEmptyOptional() {
        Integer id = 1;

        when(userDao.getUser(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.getUser(id)).isInstanceOf(ResourceNotFoundException.class).hasMessage("user with id [%s] not found".formatted(id));
    }


    @Test
    void updateUser() {
        Integer id = 1;
        User user = new User(id, "foo", "password", 10, "ROLE_USER");

        when(userDao.getUser(id)).thenReturn(Optional.of(user));

        UserUpdateRequest request = new UserUpdateRequest("boo",  20);
        when(userDao.existsUserWithUsername("boo")).thenReturn(false);

        underTest.updateUser(request, id);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao).updateUser(userArgumentCaptor.capture());

        User userCapture = userArgumentCaptor.getValue();

        assertThat(userCapture.getUsername()).isEqualTo(request.username());
        assertThat(userCapture.getDeposit()).isEqualTo(request.deposit());
    }

    @Test
    void deleteUser() {
        int id = 1;
        when(userDao.existsUserWithId(id)).thenReturn(true);
        underTest.deleteUser(id);
        verify(userDao).deleteUser(id);
    }

    @Test
    void willThrowDeleteUserByIdNotExists() {
        // Given
        int id = 10;

        when(userDao.existsUserWithId(id)).thenReturn(false);

        // When
        assertThatThrownBy(() -> underTest.deleteUser(id)).isInstanceOf(ResourceNotFoundException.class).hasMessage("user with id [%s] not found".formatted(id));

        // Then
        verify(userDao, never()).deleteUser(id);
    }
}