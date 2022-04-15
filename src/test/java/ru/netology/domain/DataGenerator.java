package ru.netology.domain;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private DataGenerator() {
    }

    private static Faker faker = new Faker(new Locale("en"));
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void makeRegistration(Registration registration) {
        given()
                .spec(requestSpec)
                .body(registration)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static Registration generateNewActiveUser(String status) {
        String login = faker.name().firstName().toLowerCase();
        String password = faker.internet().password();
        makeRegistration(new Registration(login, password, status));
        return new Registration(login, password, status);
    }


    public static Registration generateNewActiveUserInvalidLogin() {
        String password = faker.internet().password();
        String status = "active";
        makeRegistration(new Registration("vasya", password, status));
        return new Registration("login", password, status);
    }

    public static Registration generateNewActiveInvalidPassword() {
        String login = faker.name().firstName().toLowerCase();
        String status = "active";
        makeRegistration(new Registration(login, "password", status));
        return new Registration(login, "asdfgh", status);
    }
}






