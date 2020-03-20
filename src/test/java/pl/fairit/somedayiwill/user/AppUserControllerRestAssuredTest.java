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
import pl.fairit.somedayiwill.security.user.SignupEmailService;

import static io.restassured.RestAssured.given;
import static pl.fairit.somedayiwill.security.TestAuthRequest.retrieveLoginRequestBodyFromProvidedAppUser;
import static pl.fairit.somedayiwill.security.TestAuthRequest.retrieveSignupRequestBodyFromProvidedAppUser;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, value = "server.port=8084")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ContextConfiguration
@MockBean(SignupEmailService.class)
class AppUserControllerRestAssuredTest {
    private static String token;

    @BeforeEach
    public void authorization() {
        var newUser = TestUsers.aUserWithRandomCredentials();
        var signupRequest = retrieveSignupRequestBodyFromProvidedAppUser(newUser);
        var loginRequest = retrieveLoginRequestBodyFromProvidedAppUser(newUser);

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
                .port(8084)
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
        given()
                .port(8084)
                .basePath("/users/me")
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(401);
    }

    @Test
    public void shouldReturnAppUserDtoWhenValidCredentialsProvided() {
        given()
                .port(8084)
                .basePath("/users/me")
                .header("Authorization", "Bearer " + token)
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(200);
    }
}

