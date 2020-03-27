package pl.fairit.somedayiwill.book.booksearch;

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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, value = "server.port=8082")
class BookSearchControllerRestAssuredTest {
    @LocalServerPort
    private int port;

    @Test
    public void shouldReturnBooksWhenSearchByAuthorPerformed() {
        var query = "Rowling";
        given()
                .port(port)
                .get("/books/search?author=" + query)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .assertThat()
                .body("books", Matchers.notNullValue());
    }

    @Test
    public void shouldReturnBooksWhenSearchByTitlePerformed() {
        var query = "Potter";
        given()
                .port(port)
                .get("/books/search?title=" + query)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .assertThat()
                .body("books", Matchers.notNullValue());
    }
}
