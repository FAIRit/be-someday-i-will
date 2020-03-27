package pl.fairit.somedayiwill.user;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.fairit.somedayiwill.newsletter.SendGridEmailService;
import pl.fairit.somedayiwill.security.TestAuthRequest;

import java.util.Objects;

import static io.restassured.RestAssured.given;
import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.fairit.somedayiwill.security.TestAuthRequest.aLoginRequest;
import static pl.fairit.somedayiwill.security.TestAuthRequest.aSignupRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, value = "server.port=8084")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ContextConfiguration
@MockBean(SendGridEmailService.class)
class AppUserControllerRestAssuredTest {
    @LocalServerPort
    private int port;
    private static String token;


    @BeforeEach
    public void authorization() {
        if (nonNull(token)) {
            return;
        }
        var user = TestUsers.aUserWithRandomCredentials();
        var signupRequest = aSignupRequest(user);
        var loginRequest = aLoginRequest(user);

        given()
                .port(port)
                .basePath("/auth/signup")
                .contentType(ContentType.JSON)
                .body(signupRequest)
                .post();

        var authResponse = given()
                .port(port)
                .basePath("/auth/login")
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .post()
                .then()
                .extract()
                .body()
                .asString();

        token = Objects.requireNonNull(TestAuthRequest.getTokenFromJSONString(authResponse)).getAccessToken();
    }

    @Test
    public void shouldReturnUnauthorizedWhenNoCredentialsProvided() {
        given()
                .port(port)
                .basePath("/users/me")
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(401);
    }

    @Test
    public void shouldReturnAppUserDtoWhenValidCredentialsProvided() {
        var response = given()
                .port(port)
                .basePath("/users/me")
                .header("Authorization", "Bearer " + token)
                .get();
        var responseBody = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        assertTrue(responseBody.contains("email"));
    }
}

