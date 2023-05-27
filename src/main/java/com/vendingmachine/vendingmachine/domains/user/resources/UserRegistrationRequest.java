package com.vendingmachine.vendingmachine.domains.user.resources;

public record UserRegistrationRequest(String username, String password, Integer deposit, String role) {
    public  UserRegistrationRequest{
        if(deposit == null) deposit = 0;
        if(role == null) role = "ROLE_USER";
    }
}
