package com.vendingmachine.vendingmachine.journey;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.vendingmachine.vendingmachine.domains.auth.resources.AuthenticationRequest;
import com.vendingmachine.vendingmachine.domains.auth.resources.AuthenticationResponse;
import com.vendingmachine.vendingmachine.domains.user.dtos.UserDTO;
import com.vendingmachine.vendingmachine.domains.user.resources.UserRegistrationRequest;
import com.vendingmachine.vendingmachine.jwt.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AuthenticationIT {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private JWTUtil jwtUtil;

    private static final String AUTHENTICATION_PATH = "/api/v1/auth";
    private static final String USER_PATH = "/api/v1/users";

    @Test
    void canLogin() {
        // Given
// create registration UserRegistrationRequest
        Faker faker = new Faker();
        Name fakerName = faker.name();

        String name = fakerName.fullName();
        String password = "password";

        UserRegistrationRequest UserRegistrationRequest = new UserRegistrationRequest(
                name,  password, 20, "ROLE_USER"
        );

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                name,
                password
        );

        webTestClient.post()
                .uri(AUTHENTICATION_PATH + "/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus()
                .isUnauthorized();

        // send a post UserRegistrationRequest
        webTestClient.post()
                .uri(USER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        Mono.just(UserRegistrationRequest),
                        UserRegistrationRequest.class
                )
                .exchange()
                .expectStatus()
                .isOk();

        EntityExchangeResult<AuthenticationResponse> result = webTestClient.post()
                .uri(AUTHENTICATION_PATH + "/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<AuthenticationResponse>() {
                })
                .returnResult();

        String jwtToken = result.getResponseHeaders()
                .get(HttpHeaders.AUTHORIZATION)
                .get(0);

        AuthenticationResponse authenticationResponse = result.getResponseBody();

        assert authenticationResponse != null;
        UserDTO UserDTO = authenticationResponse.userDTO();

        assertThat(jwtUtil.isTokenValid(
                jwtToken,
                UserDTO.username())).isTrue();

        assertThat(UserDTO.username()).isEqualTo(name);
        assertThat(UserDTO.deposit()).isEqualTo(20);
        assertThat(UserDTO.roles()).isEqualTo(List.of("ROLE_USER"));

    }
}
