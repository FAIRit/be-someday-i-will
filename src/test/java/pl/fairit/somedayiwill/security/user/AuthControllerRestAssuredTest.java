package pl.fairit.somedayiwill.security.user;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.fairit.somedayiwill.user.TestUsers;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.fairit.somedayiwill.security.TestAuthRequest.aLoginRequest;
import static pl.fairit.somedayiwill.security.TestAuthRequest.aSignupRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, value = "server.port=8081")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ContextConfiguration
@MockBean(SignupEmailService.class)
public class AuthControllerRestAssuredTest {
    @LocalServerPort
    private int port;

    @Test
    public void whenRequestedPostToSignupThenUserCreated() {
        var userToRegister = TestUsers.aDefaultUser();
        var requestBody = aSignupRequest(userToRegister);

        var response = given()
                .port(port)
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
        var requestBody = aSignupRequest(userToRegister);
        //@formatter:off
        given()
                .port(port)
                .body(requestBody)
                .contentType(ContentType.JSON)
        .when()
                .post("/auth/signup")
        .then()
                .assertThat()
                .statusCode(201);

        given()
                .port(port)
                .body(requestBody)
                .contentType(ContentType.JSON)
        .when()
                .post("/auth/signup")
        .then()
                .assertThat()
                .statusCode(409);
        //@formatter:on
    }

    @Test
    public void whenPasswordNotValidThenShouldReturnBadRequestStatusCode() {
        var userToRegister = TestUsers.aUserWithRandomCredentials();
        userToRegister.setPassword("invalid");
        var requestBody = aSignupRequest(userToRegister);
        //@formatter:off
        given()
                .port(port)
                .body(requestBody)
                .contentType(ContentType.JSON)
        .when()
                .post("/auth/signup")
        .then()
                .assertThat()
                .statusCode(400);
        //@formatter:on

    }

    @Test
    public void shouldReturnTokenWhenValidLoginRequestPerformed() {
        var userToRegister = TestUsers.aUserWithRandomCredentials();
        var signupRequestBody = aSignupRequest(userToRegister);
        var loginRequestBody = aLoginRequest(userToRegister);

        given()
                .port(port)
                .body(signupRequestBody)
                .contentType(ContentType.JSON)
                .post("/auth/signup")
                .then()
                .statusCode(201);
        var response = given()
                .port(port)
                .body(loginRequestBody)
                .contentType(ContentType.JSON)
                .post("/auth/login");
        var responseBody = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        assertTrue(responseBody.contains("accessToken"));
    }
}
