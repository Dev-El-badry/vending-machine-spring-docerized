package com.vendingmachine.vendingmachine.domains.user.dtos;

import java.util.List;

public record UserDTO(Integer id, String username, Integer deposit, List<String> roles) {
}