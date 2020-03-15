package pl.fairit.somedayiwill.book.usersbooks;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import pl.fairit.somedayiwill.book.booksearch.GBook;
import pl.fairit.somedayiwill.book.usersbooks.Book;
import pl.fairit.somedayiwill.book.usersbooks.BookDto;
import pl.fairit.somedayiwill.book.usersbooks.BookMapper;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class BookMapperTest {

    @Test
    void shouldMapBookToBookDto() {
        var faker = new Faker();
        var book = Book.builder()
                .authors(faker.book().author())
                .buyLink(faker.internet().url())
                .imageLink(faker.internet().url())
                .title(faker.book().title())
                .description(faker.witcher().quote())
                .pageCount(faker.number().randomDigitNotZero())
                .categories(faker.book().genre())
                .build();

        var bookDto = BookMapper.INSTANCE.mapBookToBookDto(book);

        assertThat(bookDto).isNotNull();
        assertThat(bookDto.getAuthors()).isEqualTo(book.getAuthors());
        assertThat(bookDto.getBuyLink()).isEqualTo(book.getBuyLink());
        assertThat(bookDto.getImageLink()).isEqualTo(book.getImageLink());
        assertThat(bookDto.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookDto.getDescription()).isEqualTo(book.getDescription());
        assertThat(bookDto.getPageCount()).isEqualTo(book.getPageCount());
        assertThat(bookDto.getCategories()).isEqualTo(book.getCategories());
    }

    @Test
    void shouldMapBookDtoToBook() {
        var faker = new Faker();
        var bookDto = BookDto.builder()
                .authors(faker.book().author())
                .buyLink(faker.internet().url())
                .imageLink(faker.internet().url())
                .title(faker.book().title())
                .description(faker.overwatch().quote())
                .pageCount(faker.number().randomDigitNotZero())
                .categories(faker.book().genre())
                .build();

        var book = BookMapper.INSTANCE.mapBookDtoToBook(bookDto);

        assertThat(book).isNotNull();
        assertThat(book.getAuthors()).isEqualTo(bookDto.getAuthors());
        assertThat(book.getBuyLink()).isEqualTo(bookDto.getBuyLink());
        assertThat(book.getImageLink()).isEqualTo(bookDto.getImageLink());
        assertThat(book.getTitle()).isEqualTo(bookDto.getTitle());
        assertThat(book.getDescription()).isEqualTo(bookDto.getDescription().length() < 7997 ? bookDto.getDescription() : bookDto.getDescription().substring(0, 7997) + "...");
        assertThat(book.getPageCount()).isEqualTo(bookDto.getPageCount());
        assertThat(book.getCategories()).isEqualTo(bookDto.getCategories());
    }

    @Test
    void shouldMapGBookToBookDto() {
        var faker = new Faker();
        var gBook = GBook.builder()
                .authors(new String[]{faker.book().author(), faker.book().author()})
                .buyLink(faker.internet().url())
                .imageLinks(Collections.singletonMap("smallThumbnail", faker.internet().url()))
                .title(faker.book().title())
                .description(faker.harryPotter().quote())
                .pageCount(faker.number().randomDigitNotZero())
                .categories(new String[]{faker.book().genre(), faker.book().genre()})
                .build();

        var bookDto = BookMapper.INSTANCE.mapGBookToBookDto(gBook);

        assertThat(bookDto).isNotNull();
        assertThat(bookDto.getAuthors()).isEqualTo(gBook.getAuthors()[0] + ", " + gBook.getAuthors()[1]);
        assertThat(bookDto.getBuyLink()).isEqualTo(gBook.getBuyLink());
        assertThat(bookDto.getImageLink()).isEqualTo(gBook.getImageLinks().get("smallThumbnail"));
        assertThat(bookDto.getTitle()).isEqualTo(gBook.getTitle());
        assertThat(bookDto.getDescription()).isEqualTo(gBook.getDescription());
        assertThat(bookDto.getPageCount()).isEqualTo(gBook.getPageCount());
        assertThat(bookDto.getCategories()).isEqualTo(gBook.getCategories()[0] + ", " + gBook.getCategories()[1]);
    }

    @Test
    void shouldConvertStringArrayToString() {
        var faker = new Faker();
        var authorsAsAnArray = new String[]{faker.book().author(), faker.book().author()};

        var authorsAsString = BookMapper.stringArrayToString(authorsAsAnArray);

        assertThat(authorsAsString).isEqualTo(authorsAsAnArray[0] + ", " + authorsAsAnArray[1]);
    }

    @Test
    void shouldConvertImageLinksMapToString() {
        var faker = new Faker();
        var imageLinks = Collections.singletonMap("smallThumbnail", faker.internet().url());

        var imageLink = BookMapper.imageLinksMapToString(imageLinks);

        assertThat(imageLink).isEqualTo(imageLinks.get("smallThumbnail"));
    }
}
