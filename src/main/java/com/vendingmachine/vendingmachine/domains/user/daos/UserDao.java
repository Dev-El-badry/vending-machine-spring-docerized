package com.vendingmachine.vendingmachine.domains.user.daos;

import com.vendingmachine.vendingmachine.domains.user.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> getAllUsers();
    Optional<User> getUser(Integer id);
    Optional<User> getUserByUsername(String username);
    void addUser(User user);
    void updateUser(User user);
    boolean existsUserWithUsername(String username);
    boolean existsUserWithId(Integer id);
    void deleteUser(Integer id);
}
