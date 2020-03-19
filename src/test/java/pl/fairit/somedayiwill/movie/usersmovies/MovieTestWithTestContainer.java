package pl.fairit.somedayiwill.movie.usersmovies;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.fairit.somedayiwill.security.user.AuthControllerRestAssuredTest;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.user.TestUsers;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, value = "server.port=8084")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ContextConfiguration
public class MovieTestWithTestContainer {
    private static String token;
    private static AppUser user;

    @BeforeEach
    public void authorization() {
        user = TestUsers.aUserWithRandomCredentials();
        var signupRequest = AuthControllerRestAssuredTest.retrieveSignupRequestBodyFromProvidedAppUser(user);
        var loginRequest = AuthControllerRestAssuredTest.retrieveLoginRequestBodyFromProvidedAppUser(user);

        given()
                .port(8084)
                .basePath("/auth/signup")
                .contentType(ContentType.JSON)
                .body(signupRequest)
                .when()
                .post()
                .then()
                .statusCode(201);

        token = given()
                .basePath("/auth/login")
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString()
                .substring(16, 184);
    }
    //todo:
    //not authenticated user perform get, assert status code 401
    //user perform save, assert status code and response body value
    //user perform getOne, assert status code and response body value
    //user perform getAll, assert status code, number of returned movies
    //user perform deleteOne, assert status code, assert movie not present when getAll performed
    //user perform deleteAll, assert status code, assert empty list returned when getAll performed
}
