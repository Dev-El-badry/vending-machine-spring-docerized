package com.vendingmachine.vendingmachine.domains.user.controller;

import com.vendingmachine.vendingmachine.domains.user.dtos.UserDTO;
import com.vendingmachine.vendingmachine.domains.user.resources.UserRegistrationRequest;
import com.vendingmachine.vendingmachine.domains.user.resources.UserUpdateRequest;
import com.vendingmachine.vendingmachine.domains.user.services.UserService;
import com.vendingmachine.vendingmachine.jwt.JWTUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final JWTUtil jwtUtil;

    public UserController(UserService userService, JWTUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("{userId}")
    public UserDTO getUser(@PathVariable("userId") Integer userId) {
        return userService.getUser(userId);
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
        userService.addUser(request);
        String jwtToken = jwtUtil.issueToken(request.username(), request.role());
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
    }

    @PutMapping("{userId}")
    public void updateUser(@RequestBody UserUpdateRequest request, @PathVariable("userId") Integer userId) {
        userService.updateUser(request, userId);
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUser(userId);
    }

}
