package pl.fairit.somedayiwill.book.booksearch;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.fairit.somedayiwill.book.testbooks.TestGBook;
import pl.fairit.somedayiwill.book.testbooks.TestGBooks;
import pl.fairit.somedayiwill.book.usersbooks.BookMapper;
import pl.fairit.somedayiwill.book.usersbooks.Books;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GoogleBooksServiceTest {
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    GoogleBooksService booksService;

    @Test
    void shouldReturnBooksWhenAuthorGiven() {
        var query = new Faker().book().title();
        var gBooksToReturn = new GBooks(1, new GBookWrapper[]{new GBookWrapper(TestGBook.aRandomGBook())});
        var books = TestGBooks.toBooks(gBooksToReturn);

        when(restTemplate.getForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(new ResponseEntity(gBooksToReturn, HttpStatus.OK));
        var foundBooks = booksService.searchBooksByAuthor(query);

        assertEquals(foundBooks, books);
    }
}
