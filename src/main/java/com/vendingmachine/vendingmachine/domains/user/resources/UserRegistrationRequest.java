package com.vendingmachine.vendingmachine.domains.user.resources;

public record UserRegistrationRequest(String username, String password, Integer deposit) {
    public  UserRegistrationRequest{
        if(deposit == null) deposit = 0;
    }
}
