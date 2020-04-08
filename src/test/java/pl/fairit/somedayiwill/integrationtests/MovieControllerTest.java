package pl.fairit.somedayiwill.integrationtests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.fairit.somedayiwill.exceptions.ResourceNotFoundException;
import pl.fairit.somedayiwill.movie.testmovies.TestMovieDto;
import pl.fairit.somedayiwill.movie.testmovies.TestMovies;
import pl.fairit.somedayiwill.movie.usersmovies.MovieService;
import pl.fairit.somedayiwill.movie.usersmovies.Movies;
import pl.fairit.somedayiwill.newsletter.SendGridEmailService;
import pl.fairit.somedayiwill.security.TestAuthorization;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@MockBean(SendGridEmailService.class)
@ContextConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovieControllerTest {
    @LocalServerPort
    private int port;
    private static String token;

    @MockBean
    private MovieService movieService;

    @BeforeAll
    public void authorize() {
        RestAssured.port = port;
        token = TestAuthorization.getToken();
    }

    @Test
    public void shouldReturnUnauthorizedWhenGetWithNoTokePerformed() {
        //@formatter:off
        when()
                .get("/users/me/movies")
        .then()
                .assertThat()
                .statusCode(401);
        //@formatter:on
    }

    @Test
    public void shouldReturnListOfUsersMoviesWhenGetAllPerformed() {
        var moviesToReturn = TestMovies.withListOfRandomMovies(3);

        Mockito.when(movieService.getAllUsersMovies(ArgumentMatchers.anyLong()))
                .thenReturn(moviesToReturn);
        var response = given()
                .header("Authorization", "Bearer " + token)
                .get("/users/me/movies");
        var foundMovies = TestMovies.fromJSONString(response.getBody()
                .asString());

        assert nonNull(foundMovies);
        assertEquals(3, foundMovies.getMovies()
                .size());
        assertEquals(moviesToReturn, foundMovies);
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void shouldReturnSavedMovieWhenPostPerformed() {
        var movieToSave = TestMovieDto.aRandomMovieDto();
        var jsonMovieDto = TestMovieDto.asJSONString(movieToSave);

        Mockito.when(movieService.saveMovie(ArgumentMatchers.eq(movieToSave), ArgumentMatchers.anyLong()))
                .thenReturn(movieToSave);
        var response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(jsonMovieDto)
                .post("/users/me/movies");
        var returnedMovieDto = TestMovieDto.fromJSONString(response.getBody()
                .asString());

        assertEquals(movieToSave, returnedMovieDto);
        assertEquals(201, response.getStatusCode());
    }

    @Test
    public void shouldReturnMovieWhenGetMovieByIdPerformed() {
        var movieToReturn = TestMovieDto.aRandomMovieDto();
        movieToReturn.setId(7L);

        Mockito.when(movieService.getUsersMovie(ArgumentMatchers.eq(7L), ArgumentMatchers.anyLong()))
                .thenReturn(movieToReturn);
        var getResponse = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .get("/users/me/movies/" + movieToReturn.getId());
        var returnedMovie = TestMovieDto.fromJSONString(getResponse.getBody()
                .asString());

        assertEquals(movieToReturn, returnedMovie);
        assertEquals(200, getResponse.getStatusCode());
    }

    @Test
    public void shouldReturnNoContentStatusCodeAfterDeleteOnePerformed() {
        var movieId = 7L;

        //@formatter:off
        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
        .when()
                .delete("/users/me/movies/" + movieId)
        .then()
                .assertThat()
                .statusCode(204);
        //@formatter:on
    }

    @Test
    public void shouldReturnNotFoundStatusCodeWhenGetMovieByIdPerformed() {
        var movieId = 4L;

        Mockito.when(movieService.getUsersMovie(ArgumentMatchers.eq(movieId), ArgumentMatchers.anyLong()))
                .thenThrow(new ResourceNotFoundException("Movie with given id does not exist"));
        var response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .get("/users/me/movies/" + movieId);

        assertEquals(404, response.getStatusCode());
        assertTrue(response.getBody()
                .asString()
                .contains("Movie with given id does not exist"));
    }

    @Test
    public void shouldReturnedNoContentWhenDeleteAllMoviesPerformed() {
        //@formatter:off
        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
        .when()
                .delete("/users/me/movies")
        .then()
                .statusCode(204);
        //@formatter:on
    }

    @Test
    public void shouldReturnMoviesWithEmptyListWhenGetAllMoviesPerformed() {
        Mockito.when(movieService.getAllUsersMovies(ArgumentMatchers.anyLong()))
                .thenReturn(new Movies(Collections.emptyList()));
        var response = given()
                .header("Authorization", "Bearer " + token)
                .get("/users/me/movies");
        var movies = TestMovies.fromJSONString(response.getBody()
                .asString());

        assert nonNull(movies);
        assertTrue(movies.getMovies()
                .isEmpty());
        assertEquals(200, response.getStatusCode());
    }
}

