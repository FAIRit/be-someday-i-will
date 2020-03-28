package pl.fairit.somedayiwill.integrationtests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.fairit.somedayiwill.book.booksearch.GoogleBooksService;
import pl.fairit.somedayiwill.book.testbooks.TestBookDto;
import pl.fairit.somedayiwill.book.testbooks.TestBooks;
import pl.fairit.somedayiwill.book.usersbooks.Books;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookSearchControllerTest {
    @LocalServerPort
    private int port;

    @MockBean
    GoogleBooksService googleBooksService;

    @BeforeAll
    private void setup() {
        RestAssured.port = port;
    }

    @Test
    public void shouldReturnBooksWhenSearchByAuthorPerformed() {
        var query = "Rowling";
        var booksToReturn = new Books(List.of(TestBookDto.aRandomBookDto()));

        Mockito.when(googleBooksService.searchBooksByAuthor(query)).thenReturn(booksToReturn);
        var response = given()
                .get("/books/search?author=" + query);
        var foundBooks = TestBooks.fromJSONString(response.getBody().asString());

        assertEquals(200, response.getStatusCode());
        assertEquals(booksToReturn, foundBooks);
    }

    @Test
    public void shouldReturnBooksWhenSearchByTitlePerformed() {
        var query = "Rowling";
        var booksToReturn = new Books(List.of(TestBookDto.aRandomBookDto()));

        Mockito.when(googleBooksService.searchBooksByTitle(query)).thenReturn(booksToReturn);
        var response = given()
                .get("/books/search?title=" + query);
        var foundBooks = TestBooks.fromJSONString(response.getBody().asString());

        assertEquals(200, response.getStatusCode());
        assertEquals(booksToReturn, foundBooks);
    }
}
