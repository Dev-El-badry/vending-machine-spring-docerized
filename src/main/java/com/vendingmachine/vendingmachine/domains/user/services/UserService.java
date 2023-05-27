package com.vendingmachine.vendingmachine.domains.user.services;

import com.vendingmachine.vendingmachine.domains.user.daos.UserDao;
import com.vendingmachine.vendingmachine.domains.user.dtos.UserDTO;
import com.vendingmachine.vendingmachine.domains.user.dtos.UserDTOMapper;
import com.vendingmachine.vendingmachine.domains.user.entities.User;
import com.vendingmachine.vendingmachine.domains.user.resources.UserRegistrationRequest;
import com.vendingmachine.vendingmachine.domains.user.resources.UserUpdateRequest;
import com.vendingmachine.vendingmachine.exception.DuplicateResourceException;
import com.vendingmachine.vendingmachine.exception.RequestValidationException;
import com.vendingmachine.vendingmachine.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserDao userDao;
    private final UserDTOMapper userDTOMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(@Qualifier("jpa") UserDao userDao, UserDTOMapper userDTOMapper, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.userDTOMapper = userDTOMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> getUsers() {
        return userDao.getAllUsers().stream().map(userDTOMapper).collect(Collectors.toList());
    }

    public UserDTO getUser(Integer id) {
        return userDao.getUser(id).map(userDTOMapper).orElseThrow(() -> new ResourceNotFoundException("user with id [%s] not found".formatted(id)));
    }

    public void addUser(UserRegistrationRequest request) {
        String username = request.username();
        if(userDao.existsUserWithUsername(username)) {
            throw new DuplicateResourceException("username already taken");
        }

        User user = new User(
                request.username(),
                passwordEncoder.encode(request.password()),
                request.deposit(),
                request.role());

        userDao.addUser(user);
    }

    public void updateUser(UserUpdateRequest request, Integer id) {
        User user = userDao.getUser(id).orElseThrow(() -> new ResourceNotFoundException("user with id [%s] not found".formatted(id)));
        boolean changes = false;

        if(request.username() != null && !request.username().equals(user.getUsername())) {
            user.setUsername(request.username());
            changes = true;
        }

        if(request.deposit() != null && !request.deposit().equals(user.getDeposit())) {
            user.setDeposit(request.deposit());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("no data changes found");
        }

        userDao.updateUser(user);
    }

    public void deleteUser(Integer id) {
        checkUserIsExists(id);
        userDao.deleteUser(id);
    }

    public void checkUserIsExists(Integer id) {
        if (!userDao.existsUserWithId(id)) {
            throw new ResourceNotFoundException("user with id [%s] not found".formatted(id));
        }
    }
}
