package com.example.webapp.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebAppControllerIntegrationTest {
  @LocalServerPort private int port;

  @BeforeEach
  public void setUp() {
    RestAssured.port = port;
  }

  @Test
  void testCreateUserSuccess() throws Exception {
    String requestBody =
        "{\r\n  \"first_name\": \"jane\",\r\n  \"last_name\": \"Doe\",\r\n  \"password\": \"testing\",\r\n  \"username\": \"jasw@doeexample.com\"\r\n}";

    given()
        .contentType(ContentType.JSON)
        .body(requestBody)
        .when()
        .post("/v1/user")
        .then()
        .statusCode(201);
  }

    @Test
    void testCreateUserBadFirstName() throws Exception {
        String requestBody =
                "{\r\n  \"first_name\": \"Jane name is greater than 20 characters\",\r\n  \"last_name\": \"Doe\",\r\n  \"password\": \"testing\",\r\n  \"username\": \"jasw@doeexample.com\"\r\n}";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/v1/user")
                .then()
                .statusCode(400);
    }

    @Test
    void testCreateUserBadRequestUserName() throws Exception {
        String requestBody =
                "{\r\n  \"first_name\": \"Jane\",\r\n  \"last_name\": \"Doe\",\r\n  \"password\": \"testing\",\r\n  \"username\": \"jasw\"\r\n}";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/v1/user")
                .then()
                .statusCode(400);
    }
}
