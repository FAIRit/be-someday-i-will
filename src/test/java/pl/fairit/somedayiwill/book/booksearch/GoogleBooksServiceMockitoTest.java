package pl.fairit.somedayiwill.book.booksearch;

import com.github.javafaker.Faker;
import io.swagger.annotations.ApiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.fairit.somedayiwill.book.usersbooks.BookMapper;
import pl.fairit.somedayiwill.book.usersbooks.Books;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GoogleBooksServiceMockitoTest {
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    GoogleBooksService booksService;

    @Test
    void shouldReturnBooksWhenQueryGiven() {
        var faker = new Faker();
        var query = faker.book().title();
        var gBooksToReturn = retrieveGBooks();
        var books = mapGBooksToBooks(gBooksToReturn);

        when(restTemplate.getForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(new ResponseEntity(gBooksToReturn, HttpStatus.OK));
        var foundBooks = booksService.searchBooks(query);

        assertEquals(foundBooks, books);
    }

    private GBooks retrieveGBooks() {
        var items = new GBookWrapper[]{new GBookWrapper(retrieveOneGBook()), new GBookWrapper(retrieveOneGBook())};
        var totalItems = items.length;
        return new GBooks(totalItems, items);
    }

    private GBook retrieveOneGBook() {
        var faker = new Faker();
        return GBook.builder()
                .authors(new String[]{faker.book().author(), faker.book().author()})
                .buyLink(faker.internet().url())
                .categories(new String[]{faker.book().genre()})
                .description(faker.lorem().sentence())
                .imageLinks(Collections.singletonMap("smallThumbnail", faker.internet().url()))
                .pageCount(faker.number().numberBetween(10, 300))
                .publishedDate((faker.date().past(2010, TimeUnit.DAYS)).toString())
                .title(faker.book().title())
                .publisher(faker.book().publisher())
                .build();
    }

    private Books mapGBooksToBooks(final GBooks gBooks) {
        return new Books(Arrays.stream(gBooks.getItems())
                .map(GBookWrapper::getVolumeInfo)
                .map(BookMapper.INSTANCE::mapGBookToBookDto)
                .collect(Collectors.toList()));
    }
}
