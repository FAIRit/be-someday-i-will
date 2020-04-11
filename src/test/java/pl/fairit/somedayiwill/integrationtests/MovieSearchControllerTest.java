package pl.fairit.somedayiwill.integrationtests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.fairit.somedayiwill.movie.moviesearch.MDBMovieService;
import pl.fairit.somedayiwill.movie.testmovies.TestMovies;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieSearchControllerTest {
    @LocalServerPort
    private int port;

    @MockBean
    MDBMovieService movieService;

    @Test
    public void shouldReturnMoviesWhenSearchByTitlePerformed() {
        var query = "Peppa";
        var moviesToReturn = TestMovies.withListOfRandomMovies(3);

        Mockito.when(movieService.searchMoviesByTitle(query))
                .thenReturn(moviesToReturn);
        var response = given()
                .port(port)
                .get("/movies/search?title=" + query);
        var returnedMovies = TestMovies.fromJSONString(response.getBody()
                .asString());

        assert returnedMovies != null;
        assertEquals(200, response.getStatusCode());
        assertEquals(moviesToReturn, returnedMovies);
    }
}
