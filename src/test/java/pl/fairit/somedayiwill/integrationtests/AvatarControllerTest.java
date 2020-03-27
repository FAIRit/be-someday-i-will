package pl.fairit.somedayiwill.integrationtests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.fairit.somedayiwill.avatar.TestMultipartFile;
import pl.fairit.somedayiwill.newsletter.SendGridEmailService;
import pl.fairit.somedayiwill.security.TestAuthorization;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@MockBean(SendGridEmailService.class)
@ContextConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AvatarControllerTest {
    @LocalServerPort
    private int port;
    private String token;

    @BeforeAll
    public void setup() {
        token = TestAuthorization.authorize(port);
    }

    @Test
    public void shouldReturnUnauthorizedWhenGetWithNoTokePerformed() {
        //@formatter:off
        when()
                .get("/users/me/avatar")
        .then()
                .assertThat()
                .statusCode(401);
        //@formatter:on
    }

    @Test
    public void shouldReturnCreatedStatusCodeWhenPostPerformed() throws IOException {
        var validFileToSave = TestMultipartFile.aValidMultipartFileMock();

        var response = given()
                .contentType("multipart/form-data")
                .header("Authorization", "Bearer " + token)
                .multiPart("file", validFileToSave.getName(), validFileToSave.getBytes(), validFileToSave.getContentType())
                .post("/users/me/avatar");

        assertEquals(validFileToSave.getContentType(), response.getContentType());
        assertArrayEquals(validFileToSave.getBytes(), response.getBody().asByteArray());
        assertEquals(201, response.getStatusCode());
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeStatusCodeWhenPostPerformed() throws IOException {
        var invalidFileToSave = TestMultipartFile.anInvalidMultipartFileMock();

        var response = given()
                .contentType("multipart/form-data")
                .header("Authorization", "Bearer " + token)
                .multiPart("file", invalidFileToSave.getName(), invalidFileToSave.getBytes(), invalidFileToSave.getContentType())
                .post("/users/me/avatar");

        assertEquals(415, response.getStatusCode());
    }

    @Test
    public void shouldReturnNoContentWhenDeleteAvatarPerformed() throws IOException {
        //@formatter:off
        given()
                .header("Authorization", "Bearer " + token)
        .when()
                .delete("/users/me/avatar")
        .then()
                .assertThat()
                .statusCode(204);
        //@formatter:on
    }

    @Test
    public void shouldReturnNotFoundStatusCode() {
        //@formatter:off
        given()
                .header("Authorization", "Bearer " + token)
        .when()
                .get("/users/me/avatar")
        .then()
                .assertThat()
                .statusCode(404);
        //@formatter:on
    }
}
