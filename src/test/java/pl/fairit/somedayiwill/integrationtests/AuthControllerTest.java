package pl.fairit.somedayiwill.integrationtests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.fairit.somedayiwill.security.user.AuthService;
import pl.fairit.somedayiwill.security.user.SignupEmailService;
import pl.fairit.somedayiwill.security.user.UserAlreadyExistsException;
import pl.fairit.somedayiwill.user.TestUsers;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.fairit.somedayiwill.security.TestAuthorization.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@MockBean(SignupEmailService.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthControllerTest {
    @LocalServerPort
    private int port;

    @MockBean
    AuthService authService;

    @BeforeAll
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void whenRequestedPostToSignupThenUserCreated() {
        var userToRegister = TestUsers.aDefaultUser();
        var signupRequest = aSignupRequest(userToRegister);
        var requestBody = aSignupRequestAsString(userToRegister);

        Mockito.when(authService.registerUser(signupRequest)).thenReturn(userToRegister);
        var response = given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .post("/auth/signup");
        var bodyValue = response.getBody()
                .asString();

        assertTrue(bodyValue.contains("User registered successfully"));
        assertEquals(201, response.getStatusCode());
    }

    @Test
    public void whenPerformedPostSecondTimeWithTheSameCredentialsThenShouldReturnConflictStatusCode() {
        var userToRegister = TestUsers.aUserWithRandomCredentials();
        var signupRequest = aSignupRequest(userToRegister);
        var requestBody = aSignupRequestAsString(userToRegister);

        Mockito.when(authService.registerUser(signupRequest)).thenThrow(new UserAlreadyExistsException("Email address already in use."));
        var response = given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .post("/auth/signup");

        assertTrue(response.getBody().asString().contains("Email address already in use."));
        assertEquals(409, response.getStatusCode());
    }

    @Test
    public void whenPasswordNotValidThenShouldReturnBadRequestStatusCode() {
        var userToRegister = TestUsers.aUserWithRandomCredentials();
        userToRegister.setPassword("invalid");
        var signupRequest = aSignupRequest(userToRegister);
        var requestBody = aSignupRequestAsString(userToRegister);

        Mockito.when(authService.registerUser(signupRequest)).thenCallRealMethod();
        var response = given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .post("/auth/signup");

        assertTrue(response.getBody().asString().contains("Password has to be at least 8 characters and contain at least one digit, one lower case and one upper case letter"));
        assertEquals(400, response.getStatusCode());
    }

    @Test
    public void shouldReturnTokenWhenValidLoginRequestPerformed() {
        var userToLogin = TestUsers.aUserWithRandomCredentials();
        var loginRequestBody = aLoginRequestAsString(userToLogin);
        var loginRequest = aLoginRequest(userToLogin);
        var token = "token value";

        Mockito.when(authService.authenticateUser(loginRequest)).thenReturn(token);
        var response = given()
                .body(loginRequestBody)
                .contentType(ContentType.JSON)
                .post("/auth/login");
        var responseBody = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        assertTrue(responseBody.contains("accessToken"));
    }
}
