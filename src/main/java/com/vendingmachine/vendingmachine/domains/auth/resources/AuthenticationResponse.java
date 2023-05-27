package com.vendingmachine.vendingmachine.domains.auth.resources;

import com.vendingmachine.vendingmachine.domains.user.dtos.UserDTO;

public record AuthenticationResponse(String token, UserDTO userDTO) {
}
