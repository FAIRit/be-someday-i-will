package pl.fairit.somedayiwill.movie.moviesearch;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, value = "server.port=8086")
class MovieSearchControllerRestAssuredTest {
    @LocalServerPort
    private int port;

    @Test
    public void shouldReturnMoviesWhenSearchByTitlePerformed() {
        var query = "Peppa";
        given()
                .port(port)
                .get("/movies/search?title=" + query)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .assertThat()
                .body("movies", Matchers.notNullValue());
    }
}
