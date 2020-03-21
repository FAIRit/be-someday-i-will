package pl.fairit.somedayiwill.avatar;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.MimeTypeUtils;
import pl.fairit.somedayiwill.newsletter.SendGridEmailService;
import pl.fairit.somedayiwill.security.TestAuthRequest;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.user.TestUsers;

import java.io.IOException;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.fairit.somedayiwill.security.TestAuthRequest.retrieveLoginRequestBodyFromProvidedAppUser;
import static pl.fairit.somedayiwill.security.TestAuthRequest.retrieveSignupRequestBodyFromProvidedAppUser;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, value = "server.port=8088")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@MockBean(SendGridEmailService.class)
@ContextConfiguration
class AvatarControllerRestAssuredTest {
    @LocalServerPort
    private int port;
    private static String token;

    @BeforeEach
    public void authorization() {
        var user = TestUsers.aUserWithRandomCredentials();
        var signupRequest = retrieveSignupRequestBodyFromProvidedAppUser(user);
        var loginRequest = retrieveLoginRequestBodyFromProvidedAppUser(user);

        given()
                .port(port)
                .basePath("/auth/signup")
                .contentType(ContentType.JSON)
                .body(signupRequest)
                .when()
                .post()
                .then()
                .statusCode(201);

        var authResponse = given()
                .port(port)
                .basePath("/auth/login")
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        token = Objects.requireNonNull(TestAuthRequest.getTokenFromJSONString(authResponse)).getAccessToken();
    }

    @Test
    public void shouldReturnUnauthorizedWhenGetWithNoTokePerformed() {
        given()
                .port(port)
                .basePath("/users/me/avatar")
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(401);
    }

    @Test
    public void shouldReturnCreatedStatusCodeWhenPostPerformed() throws IOException {
        var validFileToSave = new MockMultipartFile("file_name", "original_name", MimeTypeUtils.IMAGE_JPEG_VALUE, "value".getBytes());

        var response = given()
                .port(port)
                .basePath("/users/me/avatar")
                .contentType("multipart/form-data")
                .header("Authorization", "Bearer " + token)
                .multiPart("file", validFileToSave.getName(), validFileToSave.getBytes(), validFileToSave.getContentType())
                .post();
        assertEquals(201, response.getStatusCode());
    }//todo: assertion on returned body

    @Test
    public void shouldReturnBadRequestStatusCodeWhenPostPerformed() throws IOException {
        var invalidFileToSave = new MockMultipartFile("file_name", "original_name", MimeTypeUtils.IMAGE_GIF_VALUE, "value".getBytes());

        var response = given()
                .port(port)
                .basePath("/users/me/avatar")
                .contentType("multipart/form-data")
                .header("Authorization", "Bearer " + token)
                .multiPart("file", invalidFileToSave.getName(), invalidFileToSave.getBytes(), invalidFileToSave.getContentType())
                .post();
        assertEquals(415, response.getStatusCode());
    }

    @Test
    public void shouldReturnNoContentWhenDeleteAvatarPerformed() throws IOException {
        var validFileToSave = new MockMultipartFile("file_name", "original_name", MimeTypeUtils.IMAGE_JPEG_VALUE, "value".getBytes());

        //save file
        given()
                .port(port)
                .basePath("/users/me/avatar")
                .contentType("multipart/form-data")
                .header("Authorization", "Bearer " + token)
                .multiPart("file", validFileToSave.getName(), validFileToSave.getBytes(), validFileToSave.getContentType())
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(201);
        //delete file
        given()
                .port(port)
                .basePath("/users/me/avatar")
                .header("Authorization", "Bearer " + token)
                .when()
                .delete()
                .then()
                .assertThat()
                .statusCode(204);
        //attempt to get deleted file
        given()
                .port(port)
                .header("Authorization", "Bearer " + token)
                .basePath("/users/me/avatar")
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(404);
    }
}