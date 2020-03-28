package pl.fairit.somedayiwill.integrationtests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.fairit.somedayiwill.newsletter.SendGridEmailService;
import pl.fairit.somedayiwill.security.TestAuthorization;
import pl.fairit.somedayiwill.user.TestUsers;

import java.util.Objects;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.fairit.somedayiwill.security.TestAuthorization.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ContextConfiguration
@MockBean(SendGridEmailService.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AppUserControllerTest {
    @LocalServerPort
    private int port;
    private String token;

    @BeforeAll
    public void authorize() {
        RestAssured.port = port;
        token = TestAuthorization.getToken();
    }

    @Test
    public void shouldReturnUnauthorizedWhenNoCredentialsProvided() {
        //@formatter:off
        when()
                .get("/users/me")
        .then()
                .assertThat()
                .statusCode(401);
        //@formatter:on
    }

    @Test
    public void shouldReturnAppUserDtoWhenValidCredentialsProvided() {
        var response = given()
                .header("Authorization", "Bearer " + token)
                .get("/users/me");
        var responseBody = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        assertTrue(responseBody.contains("email"));
    }
}

