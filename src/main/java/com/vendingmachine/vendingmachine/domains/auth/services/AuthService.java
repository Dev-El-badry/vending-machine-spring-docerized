package com.vendingmachine.vendingmachine.domains.auth.services;

import com.vendingmachine.vendingmachine.domains.auth.resources.AuthenticationRequest;
import com.vendingmachine.vendingmachine.domains.auth.resources.AuthenticationResponse;
import com.vendingmachine.vendingmachine.domains.user.dtos.UserDTO;
import com.vendingmachine.vendingmachine.domains.user.dtos.UserDTOMapper;
import com.vendingmachine.vendingmachine.domains.user.entities.User;
import com.vendingmachine.vendingmachine.jwt.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDTOMapper userDTOMapper;
    private final JWTUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager, UserDTOMapper userDTOMapper, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDTOMapper = userDTOMapper;
        this.jwtUtil = jwtUtil;
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        User principal = (User) authentication.getPrincipal();
        UserDTO userDTO = userDTOMapper.apply(principal);
        String token = jwtUtil.issueToken(userDTO.username(), userDTO.roles());
        return new AuthenticationResponse(token, userDTO);
    }
}
