package pl.fairit.somedayiwill.integrationtests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.fairit.somedayiwill.book.testbooks.TestBookDto;
import pl.fairit.somedayiwill.book.testbooks.TestBooks;
import pl.fairit.somedayiwill.book.usersbooks.BookService;
import pl.fairit.somedayiwill.book.usersbooks.Books;
import pl.fairit.somedayiwill.exceptions.ResourceNotFoundException;
import pl.fairit.somedayiwill.newsletter.SendGridEmailService;
import pl.fairit.somedayiwill.security.TestAuthorization;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@MockBean(SendGridEmailService.class)
@ContextConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookControllerTest {
    @LocalServerPort
    private int port;
    private String token;

    @MockBean
    private BookService booksService;

    @BeforeAll
    void authorize() {
        RestAssured.port = port;
        token = TestAuthorization.getToken();
    }

    @Test
    void shouldReturnUnauthorizedWhenGetWithNoTokePerformed() {
        //@formatter:off
        when()
                .get("/users/me/books")
        .then()
                .assertThat()
                .statusCode(401);
        //@formatter:on
    }

    @Test
    void shouldReturnListOfUsersBooksWhenGetAllPerformed() {
        var booksToReturn = TestBooks.withListOfRandomBooks(6);

        Mockito.when(booksService.getAllUsersBooks(anyLong()))
                .thenReturn(booksToReturn);
        var response = given()
                .header("Authorization", "Bearer " + token)
                .get("/users/me/books");
        var foundBooks = TestBooks.fromJSONString(response.getBody()
                .asString());

        assert nonNull(foundBooks);
        assertEquals(6, foundBooks.getBookDtos()
                .size());
        assertEquals(booksToReturn, foundBooks);
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void shouldReturnSavedBookWhenPostPerformed() {
        var bookToSave = TestBookDto.aRandomBookDto();
        var jsonBookDto = TestBookDto.asJSONString(bookToSave);

        Mockito.when(booksService.saveBook(eq(bookToSave), anyLong()))
                .thenReturn(bookToSave);
        var response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(jsonBookDto)
                .post("/users/me/books");
        var returnedBookDto = TestBookDto.fromJSONString(response.getBody()
                .asString());

        assertEquals(bookToSave, returnedBookDto);
        assertEquals(201, response.getStatusCode());
    }

    @Test
    void shouldReturnBookWhenGetBookByIdPerformed() {
        var bookToReturn = TestBookDto.aRandomBookDto();
        bookToReturn.setId(3L);

        Mockito.when(booksService.getUsersBook(eq(3L), anyLong()))
                .thenReturn(bookToReturn);
        var response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .get("/users/me/books/" + bookToReturn.getId());
        var returnedBook = TestBookDto.fromJSONString(response.getBody()
                .asString());

        assertEquals(bookToReturn, returnedBook);
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void shouldReturnNoContentStatusCodeAfterDeleteBookByIdPerformed() {
        var bookId = 4L;

        //@formatter:off
        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
        .when()
                .delete("/users/me/books/" + bookId)
        .then()
                .assertThat()
                .statusCode(204);
        //@formatter:on
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenGetBookByIdPerformed() {
        var bookId = 6L;

        Mockito.when(booksService.getUsersBook(eq(bookId), anyLong()))
                .thenThrow(new ResourceNotFoundException("Book with given id does not exist"));
        var response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .get("/users/me/books/" + bookId);

        assertEquals(404, response.getStatusCode());
        assertTrue(response.getBody()
                .asString()
                .contains("Book with given id does not exist"));
    }

    @Test
    void shouldReturnedNoContentWhenDeleteAllBooksPerformed() {
        //@formatter:off
        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
        .when()
                .delete("/users/me/books")
                .then()
        .statusCode(204);
        //@formatter:on
    }

    @Test
    void shouldReturnBooksWithEmptyListWhenGetAllBooksPerformed() {
        Mockito.when(booksService.getAllUsersBooks(anyLong()))
                .thenReturn(new Books(Collections.emptyList()));
        var response = given()
                .header("Authorization", "Bearer " + token)
                .get("/users/me/books");
        var books = TestBooks.fromJSONString(response.getBody()
                .asString());

        assert nonNull(books);
        assertTrue(books.getBookDtos()
                .isEmpty());
        assertEquals(200, response.getStatusCode());
    }
}
