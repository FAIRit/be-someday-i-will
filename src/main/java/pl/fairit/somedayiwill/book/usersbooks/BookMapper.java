package pl.fairit.somedayiwill.book.usersbooks;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import pl.fairit.somedayiwill.book.booksearch.GBook;

import java.util.Map;
import java.util.StringJoiner;

import static java.util.Objects.isNull;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDto mapBookToBookDto(Book book);

    Book mapBookDtoToBook(BookDto bookDto);

    @Mapping(source = "imageLinks", target = "imageLink", qualifiedByName = "imageLinksMapToString")
    @Mapping(source = "authors", target = "authors", qualifiedByName = "stringArrayToString")
    @Mapping(source = "categories", target = "categories", qualifiedByName = "stringArrayToString")
    BookDto mapGBookToBookDto(GBook gBook);

    @Named("stringArrayToString")
    static String stringArrayToString(String[] array) {
        if (isNull(array)) {
            return "";
        }
        var joiner = new StringJoiner(", ");
        for (String s : array) {
            joiner.add(s);
        }
        return joiner.toString();
    }

    @Named("imageLinksMapToString")
    static String imageLinksMapToString(final Map<String, String> imageLinks) {
        return imageLinks.get("smallThumbnail");
    }
}
