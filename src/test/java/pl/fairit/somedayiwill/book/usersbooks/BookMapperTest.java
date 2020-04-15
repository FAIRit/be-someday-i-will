package pl.fairit.somedayiwill.book.usersbooks;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import pl.fairit.somedayiwill.book.booksearch.GBook;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class BookMapperTest {
    private final static Faker FAKER = new Faker();

    @Test
    void shouldMapBookToBookDto() {
        var book = Book.builder()
                .authors(FAKER.book().author())
                .buyLink(FAKER.internet().url())
                .imageLink(FAKER.internet().url())
                .title(FAKER.book().title())
                .description(FAKER.witcher().quote())
                .pageCount(FAKER.number().randomDigitNotZero())
                .categories(FAKER.book().genre())
                .build();

        var bookDto = BookMapper.INSTANCE.mapBookToBookDto(book);

        assertAll(
                () -> assertThat(bookDto).isNotNull(),
                () -> assertThat(bookDto.getAuthors()).isEqualTo(book.getAuthors()),
                () -> assertThat(bookDto.getBuyLink()).isEqualTo(book.getBuyLink()),
                () -> assertThat(bookDto.getImageLink()).isEqualTo(book.getImageLink()),
                () -> assertThat(bookDto.getTitle()).isEqualTo(book.getTitle()),
                () -> assertThat(bookDto.getDescription()).isEqualTo(book.getDescription()),
                () -> assertThat(bookDto.getPageCount()).isEqualTo(book.getPageCount()),
                () -> assertThat(bookDto.getCategories()).isEqualTo(book.getCategories())
        );
    }

    @Test
    void shouldMapBookDtoToBook() {
        var bookDto = BookDto.builder()
                .authors(FAKER.book().author())
                .buyLink(FAKER.internet().url())
                .imageLink(FAKER.internet().url())
                .title(FAKER.book().title())
                .description(FAKER.overwatch().quote())
                .pageCount(FAKER.number().randomDigitNotZero())
                .categories(FAKER.book().genre())
                .build();

        var book = BookMapper.INSTANCE.mapBookDtoToBook(bookDto);
        assertAll(
                () -> assertThat(book).isNotNull(),
                () -> assertThat(book.getAuthors()).isEqualTo(bookDto.getAuthors()),
                () -> assertThat(book.getBuyLink()).isEqualTo(bookDto.getBuyLink()),
                () -> assertThat(book.getImageLink()).isEqualTo(bookDto.getImageLink()),
                () -> assertThat(book.getTitle()).isEqualTo(bookDto.getTitle()),
                () -> assertThat(book.getDescription()).isEqualTo(bookDto.getDescription()
                        .length() < 7997 ? bookDto.getDescription() : bookDto.getDescription()
                        .substring(0, 7997) + "..."),
                () -> assertThat(book.getPageCount()).isEqualTo(bookDto.getPageCount()),
                () -> assertThat(book.getCategories()).isEqualTo(bookDto.getCategories())
        );
    }

    @Test
    void shouldMapGBookToBookDto() {
        var gBook = GBook.builder()
                .authors(new String[]{FAKER.book().author(), FAKER.book().author()})
                .canonicalVolumeLink(FAKER.internet().url())
                .imageLinks(Collections.singletonMap("smallThumbnail", FAKER.internet().url()))
                .title(FAKER.book().title())
                .description(FAKER.harryPotter().quote())
                .pageCount(FAKER.number().randomDigitNotZero())
                .categories(new String[]{FAKER.book().genre(), FAKER.book().genre()})
                .build();

        var bookDto = BookMapper.INSTANCE.mapGBookToBookDto(gBook);
        assertAll(
                () -> assertThat(bookDto).isNotNull(),
                () -> assertThat(bookDto.getAuthors()).isEqualTo(gBook.getAuthors()[0] + ", " + gBook.getAuthors()[1]),
                () -> assertThat(bookDto.getBuyLink()).isEqualTo(gBook.getCanonicalVolumeLink()),
                () -> assertThat(bookDto.getImageLink()).isEqualTo(gBook.getImageLinks().get("smallThumbnail")),
                () -> assertThat(bookDto.getTitle()).isEqualTo(gBook.getTitle()),
                () -> assertThat(bookDto.getDescription()).isEqualTo(gBook.getDescription()),
                () -> assertThat(bookDto.getPageCount()).isEqualTo(gBook.getPageCount()),
                () -> assertThat(bookDto.getCategories())
                        .isEqualTo(gBook.getCategories()[0] + ", " + gBook.getCategories()[1])
        );
    }

    @Test
    void shouldConvertStringArrayToString() {
        var authorsAsAnArray = new String[]{FAKER.book().author(), FAKER.book().author()};

        var authorsAsString = BookMapper.stringArrayToString(authorsAsAnArray);

        assertThat(authorsAsString).isEqualTo(authorsAsAnArray[0] + ", " + authorsAsAnArray[1]);
    }

    @Test
    void shouldConvertImageLinksMapToString() {
        var imageLinks = Collections.singletonMap("smallThumbnail", FAKER.internet().url());

        var imageLink = BookMapper.imageLinksMapToString(imageLinks);

        assertThat(imageLink).isEqualTo(imageLinks.get("smallThumbnail"));
    }
}
