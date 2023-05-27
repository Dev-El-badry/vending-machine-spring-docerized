package com.vendingmachine.vendingmachine.domains.user.dtos;

import org.springframework.security.core.GrantedAuthority;
import com.vendingmachine.vendingmachine.domains.user.entities.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserDTOMapper implements Function<User, UserDTO>{
    @Override
    public UserDTO apply(User user){
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getDeposit(),
                user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        );
    }
}
