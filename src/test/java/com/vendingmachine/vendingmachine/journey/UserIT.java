package com.vendingmachine.vendingmachine.journey;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.vendingmachine.vendingmachine.domains.user.dtos.UserDTO;
import com.vendingmachine.vendingmachine.domains.user.resources.UserRegistrationRequest;
import com.vendingmachine.vendingmachine.domains.user.resources.UserUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UserIT {

    @Autowired
    private WebTestClient webTestClient;

    private static final String USER_PATH = "/api/v1/users";

    @Test
    void canRegisterUser() {
        // create registration request
        Faker faker = new Faker();
        Name fakerName = faker.name();

        String name = fakerName.fullName();

        UserRegistrationRequest request = new UserRegistrationRequest(
                name, "password", 20, "ROLE_USER"
        );
        // send a post request
        String jwtToken = webTestClient.post()
                .uri(USER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), UserRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Void.class)
                .getResponseHeaders()
                .get(AUTHORIZATION)
                .get(0);

        // get all users
        List<UserDTO> allUsers = webTestClient.get()
                .uri(USER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<UserDTO>() {
                })
                .returnResult()
                .getResponseBody();

        int id = allUsers.stream()
                .filter(user -> user.username().equals(name))
                .map(UserDTO::id)
                .findFirst()
                .orElseThrow();

        // make sure that user is present
        UserDTO expectedUser = new UserDTO(
                id,
                name,
                20,
                List.of("ROLE_USER")
        );

        assertThat(allUsers).contains(expectedUser);

        // get user by id
        webTestClient.get()
                .uri(USER_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<UserDTO>() {
                })
                .isEqualTo(expectedUser);
    }

    @Test
    void canDeleteUser() {
        // create registration request
        Faker faker = new Faker();
        Name fakerName = faker.name();

        String name = fakerName.fullName();

        UserRegistrationRequest request = new UserRegistrationRequest(
                name, "password", 20, "ROLE_USER"
        );

        UserRegistrationRequest request2 = new UserRegistrationRequest(
                name+"_uk", "password", 100, "ROLE_USER"
        );

        // send a post request to create user 1
        webTestClient.post()
                .uri(USER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), UserRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // send a post request to create user 2
        String jwtToken = webTestClient.post()
                .uri(USER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request2), UserRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Void.class)
                .getResponseHeaders()
                .get(AUTHORIZATION)
                .get(0);

        // get all users
        List<UserDTO> allUsers = webTestClient.get()
                .uri(USER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<UserDTO>() {
                })
                .returnResult()
                .getResponseBody();


        int id = allUsers.stream()
                .filter(user -> user.username().equals(name))
                .map(UserDTO::id)
                .findFirst()
                .orElseThrow();

        // user 2 deletes user 1
        webTestClient.delete()
                .uri(USER_PATH + "/{id}", id)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        // user 2 gets user 1 by id
        webTestClient.get()
                .uri(USER_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void canUpdateUser() {
        // create registration request
        Faker faker = new Faker();
        Name fakerName = faker.name();

        String name = fakerName.firstName();

        UserRegistrationRequest request = new UserRegistrationRequest(
                name, "password", 20, "ROLE_USER"
        );

        // send a post request
        String jwtToken = webTestClient.post()
                .uri(USER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), UserRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Void.class)
                .getResponseHeaders()
                .get(AUTHORIZATION)
                .get(0);

        // get all users
        List<UserDTO> allUsers = webTestClient.get()
                .uri(USER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<UserDTO>() {
                })
                .returnResult()
                .getResponseBody();


        int id = allUsers.stream()
                .filter(user -> user.username().equals(name))
                .map(UserDTO::id)
                .findFirst()
                .orElseThrow();

        // update user

        String newName = faker.name().firstName() + "_" + UUID.randomUUID();

        UserUpdateRequest updateRequest = new UserUpdateRequest(
                newName, 50
        );

        webTestClient.put()
                .uri(USER_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateRequest), UserUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // get user by id
        UserDTO updatedUser = webTestClient.get()
                .uri(USER_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(UserDTO.class)
                .returnResult()
                .getResponseBody();

        // make sure that user is present
        UserDTO expectedUser = new UserDTO(
                id,
                newName,
                20,
                List.of("ROLE_USER")
        );

        assertThat(updatedUser).isEqualTo(expectedUser);
    }
}
