package pl.fairit.somedayiwill.book;

import org.junit.jupiter.api.Test;
import pl.fairit.somedayiwill.googlebooksapi.VolumeInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BookMapperTest {

    @Test
    void shouldMapBookToBookDto() {
        var book = Book.builder()
                .authors("Author")
                .buyLink("link to shop")
                .imageLink("link to image")
                .title("Title")
                .description("Someday i will read his fantastic book")
                .pageCount(34)
                .categories("crime, adventure")
                .build();

        var bookDto = BookMapper.INSTANCE.mapBookToBookDto(book);
        assertThat(bookDto).isNotNull();
        assertThat(bookDto.getAuthors()).isEqualTo("Author");
        assertThat(bookDto.getBuyLink()).isEqualTo("link to shop");
        assertThat(bookDto.getImageLink()).isEqualTo("link to image");
        assertThat(bookDto.getTitle()).isEqualTo("Title");
        assertThat(bookDto.getDescription()).isEqualTo("Someday i will read his fantastic book");
        assertThat(bookDto.getPageCount()).isEqualTo(34);
        assertThat(bookDto.getCategories()).isEqualTo("crime, adventure");
    }

    @Test
    void shouldMapBookDtoToBook() {
        var bookDto = BookDto.builder()
                .authors("Author")
                .buyLink("link to shop")
                .imageLink("link to image")
                .title("Title")
                .description("Someday i will read his fantastic book")
                .pageCount(34)
                .categories("crime, adventure")
                .build();

        var book = BookMapper.INSTANCE.mapBookDtoToBook(bookDto);
        assertThat(book).isNotNull();
        assertThat(book.getAuthors()).isEqualTo("Author");
        assertThat(book.getBuyLink()).isEqualTo("link to shop");
        assertThat(book.getImageLink()).isEqualTo("link to image");
        assertThat(book.getTitle()).isEqualTo("Title");
        assertThat(book.getDescription()).isEqualTo("Someday i will read his fantastic book");
        assertThat(book.getPageCount()).isEqualTo(34);
        assertThat(book.getCategories()).isEqualTo("crime, adventure");
    }

    @Test
    void shouldMapVolumeInfoToBookDto() {
        var volumeInfo = VolumeInfo.builder()
                .authors(new String[]{"Author1", "Author2"})
                .buyLink("link to shop")
                .imageLinks(Collections.singletonMap("smallThumbnail", "link to image"))
                .title("Title")
                .description("Someday i will read his fantastic book")
                .pageCount(34)
                .categories(new String[]{"crime, adventure"})
                .build();

        var bookDto = BookMapper.INSTANCE.mapVolumeInfoToBookDto(volumeInfo);
        assertThat(bookDto).isNotNull();
        assertThat(bookDto.getAuthors()).isEqualTo("Author1, Author2");
        assertThat(bookDto.getBuyLink()).isEqualTo("link to shop");
        assertThat(bookDto.getImageLink()).isEqualTo("link to image");
        assertThat(bookDto.getTitle()).isEqualTo("Title");
        assertThat(bookDto.getDescription()).isEqualTo("Someday i will read his fantastic book");
        assertThat(bookDto.getPageCount()).isEqualTo(34);
        assertThat(bookDto.getCategories()).isEqualTo("crime, adventure");

    }

    @Test
    void shouldConvertStringArrayToString() {
        String[] authorsAsAnArray = new String[]{"Author1", "Author2"};
        var authorsAsString = BookMapper.stringArrayToString(authorsAsAnArray);
        assertThat(authorsAsString).isEqualTo("Author1, Author2");
    }

    @Test
    void shouldConvertImageLinksMapToString() {
        Map<String, String> imageLinks = Collections.singletonMap("smallThumbnail", "link to image");
        var imageLink = BookMapper.imageLinksMapToString(imageLinks);
        assertThat(imageLink).isEqualTo("link to image");
    }
}
