package pl.fairit.somedayiwill.integrationtests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import pl.fairit.somedayiwill.avatar.AvatarService;
import pl.fairit.somedayiwill.avatar.TestAvatar;
import pl.fairit.somedayiwill.avatar.TestMultipartFile;
import pl.fairit.somedayiwill.exceptions.ResourceNotFoundException;
import pl.fairit.somedayiwill.newsletter.SendGridEmailService;
import pl.fairit.somedayiwill.security.TestAuthorization;
import pl.fairit.somedayiwill.user.AppUser;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@MockBean(SendGridEmailService.class)
@ContextConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AvatarControllerTest {
    @MockBean
    AvatarService avatarService;

    @LocalServerPort
    private int port;
    private String token;

    @BeforeAll
    public void authorize() {
        RestAssured.port = port;
        token = TestAuthorization.getToken();
    }

    @Test
    public void shouldReturnUnauthorizedWhenGetWithNoTokenPerformed() {
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
        var avatarToReturn = TestAvatar.fromMultipartFile(validFileToSave);

        Mockito.when(avatarService.saveAvatar(ArgumentMatchers.any(MultipartFile.class), ArgumentMatchers.any(AppUser.class)))
                .thenReturn(avatarToReturn);
        var response = given()
                .contentType("multipart/form-data")
                .header("Authorization", "Bearer " + token)
                .multiPart("file", validFileToSave.getName(), validFileToSave.getBytes(), validFileToSave.getContentType())
                .post("/users/me/avatar");

        assertEquals(validFileToSave.getContentType(), response.getContentType());
        assertArrayEquals(validFileToSave.getBytes(), response.getBody()
                .asByteArray());
        assertEquals(201, response.getStatusCode());
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeStatusCodeWhenPostPerformed() throws IOException {
        var invalidFileToSave = TestMultipartFile.anInvalidMultipartFileMock();

        Mockito.when(avatarService.saveAvatar(ArgumentMatchers.any(MultipartFile.class), ArgumentMatchers.any(AppUser.class)))
                .thenCallRealMethod();
        var response = given()
                .contentType("multipart/form-data")
                .header("Authorization", "Bearer " + token)
                .multiPart("file", invalidFileToSave.getName(), invalidFileToSave.getBytes(), invalidFileToSave.getContentType())
                .post("/users/me/avatar");

        assertEquals(415, response.getStatusCode());
        assertTrue(response.getBody()
                .asString()
                .contains("Unsupported file type."));
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
        Mockito.when(avatarService.getUsersAvatar(ArgumentMatchers.anyLong()))
                .thenThrow(new ResourceNotFoundException("Avatar does not exist"));

        //@formatter:off
       var response = given()
                .header("Authorization", "Bearer " + token)
        .when()
                .get("/users/me/avatar")
        .then()
                .assertThat()
                .statusCode(404)
        .and()
                .extract()
                .body()
                .asString();
        //@formatter:on
        assertTrue(response.contains("Avatar does not exist"));
    }

    @Test
    public void shouldReturnAvatarWhenGetPerformed() throws IOException {
        var file = TestMultipartFile.aValidMultipartFileMock();
        var avatarToReturn = TestAvatar.fromMultipartFile(file);

        Mockito.when(avatarService.getUsersAvatar(ArgumentMatchers.anyLong()))
                .thenReturn(avatarToReturn);
        var response = given()
                .header("Authorization", "Bearer " + token)
                .get("/users/me/avatar");

        assertEquals(avatarToReturn.getFileType(), response.getContentType());
        assertArrayEquals(avatarToReturn.getData(), response.getBody()
                .asByteArray());
        assertEquals(200, response.getStatusCode());
    }
}
