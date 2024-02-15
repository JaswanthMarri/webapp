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
        "{\r\n  \"first_name\": \"jane1\",\r\n  \"last_name\": \"Doe\",\r\n  \"password\": \"testing\",\r\n  \"username\": \"jasw1@doeexample.com\"\r\n}";

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

  @Test
  void testCreateUser1() throws Exception {
    String requestBody =
        "{\r\n  \"first_name\": \"Jane\",\r\n  \"last_name\": \"Doe\",\r\n  \"password\": \"testing\",\r\n  \"username\": \"jasw@doeexample.com\"\r\n}";

    given()
        .contentType(ContentType.JSON)
        .body(requestBody)
        .when()
        .post("/v1/user")
        .then()
        .statusCode(201);

    given()
        .auth()
        .basic("jasw@doeexample.com", "testing")
        .contentType(ContentType.JSON)
        .when()
        .get("/v1/user/self")
        .then()
        .statusCode(200);
  }

  @Test
  void testCreateUser2() throws Exception {
    String requestBody =
        "{\r\n  \"first_name\": \"Jane\",\r\n  \"last_name\": \"Doe\",\r\n  \"password\": \"testing1\",\r\n  \"username\": \"jasw2@doeexample.com\"\r\n}";

    given()
        .port(port)
        .contentType(ContentType.JSON)
        .body(requestBody)
        .when()
        .post("/v1/user")
        .then()
        .statusCode(201);

    String updatedBody =
        "{\r\n  \"first_name\": \"Jaswanth\",\r\n  \"last_name\": \"Doe\",\r\n  \"password\": \"testing1\",\r\n  \"username\": \"jasw2@doeexample.com\"\r\n}";

    given()
        .port(port)
        .auth()
        .basic("jasw2@doeexample.com", "testing1")
        .contentType(ContentType.JSON)
        .body(updatedBody)
        .when()
        .put("/v1/user/self")
        .then()
        .statusCode(204);

    given()
        .port(port)
        .auth()
        .basic("jasw2@doeexample.com", "testing1")
        //.contentType(ContentType.JSON)
        .when()
        .get("/v1/user/self")
        .then()
        .statusCode(200)
        .body("first_name", equalToIgnoringCase("Jaswanth"));
  }
}
