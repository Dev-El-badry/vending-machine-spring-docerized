package com.vendingmachine.vendingmachine.domains.user.repositories;

import com.vendingmachine.vendingmachine.domains.user.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsUserByUsername(String username);
    boolean existsUserById(Integer id);
    Optional<User> findUserByUsername(String username);
}
