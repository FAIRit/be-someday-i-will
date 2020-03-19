package pl.fairit.somedayiwill.security.user;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.user.TestUsers;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, value = "server.port=8081")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ContextConfiguration
@MockBean(SignupEmailService.class)
public class AuthControllerRestAssuredTest {
    @Test
    public void whenRequestedPostToSignupThenUserCreated() {
        var userToRegister = TestUsers.aDefaultUser();
        var requestBody = retrieveSignupRequestBodyFromProvidedAppUser(userToRegister);

        var response = given()
                .port(8081)
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .post("/auth/signup");
        var bodyValue = response.getBody().asString();

        assertEquals("User registered successfully", bodyValue);
        assertEquals(201, response.getStatusCode());
    }

    @Test
    public void whenPerformedPostSecondTimeWithTheSameCredentialsThenShouldReturnConflictStatusCode() {
        var userToRegister = TestUsers.aUserWithRandomCredentials();
        var requestBody = retrieveSignupRequestBodyFromProvidedAppUser(userToRegister);

        given()
                .port(8081)
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .post("/auth/signup")
                .then()
                .statusCode(201);

        given()
                .port(8081)
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .post("/auth/signup")
                .then()
                .statusCode(409);
    }

    @Test
    public void whenPasswordNotValidThenShouldReturnBadRequestStatusCode() {
        var userToRegister = TestUsers.aUserWithRandomCredentials();
        userToRegister.setPassword("invalid");
        var requestBody = retrieveSignupRequestBodyFromProvidedAppUser(userToRegister);

        given()
                .port(8081)
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .post("/auth/signup")
                .then()
                .statusCode(400);
    }

    @Test
    public void shouldReturnTokenWhenValidLoginRequestPerformed() {
        var userToRegister = TestUsers.aUserWithRandomCredentials();
        var signupRequestBody = retrieveSignupRequestBodyFromProvidedAppUser(userToRegister);
        var loginRequestBody = retrieveLoginRequestBodyFromProvidedAppUser(userToRegister);

        given()
                .port(8081)
                .body(signupRequestBody)
                .contentType(ContentType.JSON)
                .post("/auth/signup")
                .then()
                .statusCode(201);
        var response = given()
                .port(8081)
                .body(loginRequestBody)
                .contentType(ContentType.JSON)
                .post("/auth/login");
        var responseBody = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        assertTrue(responseBody.contains("accessToken"));
    }

    public static String retrieveLoginRequestBodyFromProvidedAppUser(final AppUser userToLogin) {
        return new StringBuffer()
                .append("{\"email\":\"")
                .append(userToLogin.getEmail())
                .append("\", \"password\": \"")
                .append(userToLogin.getPassword())
                .append("\"}")
                .toString();
    }

    public static String retrieveSignupRequestBodyFromProvidedAppUser(final AppUser userToRegister) {
        return new StringBuffer()
                .append("{\"name\":\"")
                .append(userToRegister.getName())
                .append("\", \"email\": \"")
                .append(userToRegister.getEmail())
                .append("\", \"password\": \"")
                .append(userToRegister.getPassword())
                .append("\"}")
                .toString();
    }
}
