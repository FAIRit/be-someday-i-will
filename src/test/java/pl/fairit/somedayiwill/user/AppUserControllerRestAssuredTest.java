package pl.fairit.somedayiwill.user;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.fairit.somedayiwill.security.user.AuthControllerRestAssuredTest;
import pl.fairit.somedayiwill.security.user.SignupEmailService;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, value = "server.port=8084")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ContextConfiguration
@MockBean(SignupEmailService.class)
class AppUserControllerRestAssuredTest {
    private static String token;

    @BeforeEach
    public void authorization() {
        var newUser = TestUsers.aUserWithRandomCredentials();
        var signupRequest = AuthControllerRestAssuredTest.retrieveSignupRequestBodyFromProvidedAppUser(newUser);
        var loginRequest = AuthControllerRestAssuredTest.retrieveLoginRequestBodyFromProvidedAppUser(newUser);

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

    @Test
    public void shouldReturnUnauthorizedWhenNoCredentialsProvided() {
        get("/users/me")
                .then()
                .assertThat()
                .statusCode(401);
    }

    @Test
    public void shouldReturnAppUserDtoWhenValidCredentialsProvided() {
        given()
                .header("Authorization", "Bearer " + token)
                .get("/users/me")
                .then()
                .statusCode(200);
    }
}

