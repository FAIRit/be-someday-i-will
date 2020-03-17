package pl.fairit.somedayiwill.book.booksearch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.get;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class BookSearchControllerRestAssuredTest {
    @Test
    public void shouldReturnBooksWhenSearchWhenAuthorGiven() {
        var query = "Rowling";
        get("/books/search?author=" + query)
                .then()
                .assertThat()
                .statusCode(200);
    }
}
