# Design API For Vending Machine

## Welcome

Welcome on vending-machine board

You will be find details about project bellow.

App will run on `3000` port.

## Security Flow Diagram
![Alt text](https://i.imgur.com/2gX418W.png)

## Usage

To get started, make sure you have Docker installed on your system, and then clone this repository.

Next, navigate in your terminal to the directory you cloned this, and spin up the containers for the web server by running


```sh
docker-compose up -d --build
```

Bringing up the Docker Compose network site instead of just using up, ensures that only our site's containers are brought up at the start, instead of all of the command containers as well. The following are built for our web server, with their exposed ports detailed:

- **postgreSQL** - `:5432`
- **api** - `:3000`
- **adminer** - `:8080`


You can access your application via localhost, if you're running the containers directly
[link] (http://localhost:3000)

## API Collection of vending machine app on postman

[vending machine app collection](https://documenter.getpostman.com/view/3000372/2s93m7X2gk)

## Running the tests

To get started, make sure you have NODE installed on your system,.

Next, Build and run Unit/Integration Tests with Maven, run command below:

`mvn -ntp -B verify`

## Application Tech Stack
The application developed with
* java 17
* Springboot
* Postgres
* spring-boot-starter-validation
* spring-boot-starter-test, mockito and junit5
* spring-boot-starter-webflux
* flyway-core
* spring-security-test
* jsonwebtoken
* testcontainers
* spring-boot-starter-data-jpa

<b>Version 3.0.7 was used for all dependencies related to spring!</b>

## Continuous Integration

- [x] To enables better transparency and farsightedness in the process of software development and delivery

## API

- [x] implemented JWT based security in a test Core Web API REST project

## Authentication

- [x] Login

## Users
- [x] Register
- [x] Update
- [x] Delete
- [x] Show

## Products

- [x] Create
- [x] Update
- [x] Delete
- [x] Show

## Orders

- [x] Buy item