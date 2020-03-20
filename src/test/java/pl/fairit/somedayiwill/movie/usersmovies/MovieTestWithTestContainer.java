package pl.fairit.somedayiwill.movie.usersmovies;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.fairit.somedayiwill.movie.testmovies.TestMovieDto;
import pl.fairit.somedayiwill.newsletter.SendGridEmailService;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.user.TestUsers;

import static io.restassured.RestAssured.given;
import static pl.fairit.somedayiwill.security.TestAuthRequest.retrieveLoginRequestBodyFromProvidedAppUser;
import static pl.fairit.somedayiwill.security.TestAuthRequest.retrieveSignupRequestBodyFromProvidedAppUser;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, value = "server.port=8085")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@MockBean(SendGridEmailService.class)
@ContextConfiguration
public class MovieTestWithTestContainer {
    private static String token;
    private static AppUser user;

    @BeforeEach
    public void authorization() {
        user = TestUsers.aUserWithRandomCredentials();
        var signupRequest = retrieveSignupRequestBodyFromProvidedAppUser(user);
        var loginRequest = retrieveLoginRequestBodyFromProvidedAppUser(user);

        given()
                .port(8085)
                .basePath("/auth/signup")
                .contentType(ContentType.JSON)
                .body(signupRequest)
                .when()
                .post()
                .then()
                .statusCode(201);

        token = given()
                .port(8085)
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
    public void shouldReturnUnauthorizedWhenGetWithNoTokePerformed() {
        given()
                .port(8085)
                .basePath("/users/me/movies")
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(401);
    }

    //user perform getAll, assert status code, number of returned movies
    @Test
    public void findMeAName() {
        given()
                .port(8085)
                .basePath("/users/me/movies")
                .header("Authorization", "Bearer " + token)
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(200);
    }

    //user perform save, assert status code and todo: response body value
    @Test
    public void iNeedNameToo() {
        var movieToSave = TestMovieDto.aRandomMovieDto();
        var jsonMovieDto = TestMovieDto.asJSONString(movieToSave);

        given()
                .port(8085)
                .basePath("/users/me/movies")
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(jsonMovieDto)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(201);
    }


    //todo:
    //user perform getOne, assert status code and response body value
    //user perform deleteOne, assert status code, assert movie not present when getAll performed
    //user perform deleteAll, assert status code, assert empty list returned when getAll performed
}
