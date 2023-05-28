package com.vendingmachine.vendingmachine.domains.user.resources;

import jakarta.validation.constraints.Pattern;

public record UserDepositRequest(
        @Pattern(regexp = "5|10|20|50|100", message = "must insert the amount of banknotes 5|10|20|50|100") String deposit) {
}
