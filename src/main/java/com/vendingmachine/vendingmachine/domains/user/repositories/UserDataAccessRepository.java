package com.vendingmachine.vendingmachine.domains.user.repositories;

import com.vendingmachine.vendingmachine.domains.user.daos.UserDao;
import com.vendingmachine.vendingmachine.domains.user.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class UserDataAccessRepository implements UserDao {
    public final UserRepository userRepository;

    public UserDataAccessRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean existsUserWithUsername(String username) {
        return userRepository.existsUserByUsername(username);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean existsUserWithId(Integer id) {
        return userRepository.existsUserById(id);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
