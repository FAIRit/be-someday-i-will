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
    int DESCRIPTION_MAX_LENGTH = 997;

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDto mapBookToBookDto(Book book);

    @Mapping(source = "description", target = "description", qualifiedByName = "trimToLongDescription")
    Book mapBookDtoToBook(BookDto bookDto);

    @Mapping(source = "imageLinks", target = "imageLink", qualifiedByName = "imageLinksMapToString")
    @Mapping(source = "authors", target = "authors", qualifiedByName = "stringArrayToString")
    @Mapping(source = "categories", target = "categories", qualifiedByName = "stringArrayToString")
    @Mapping(source = "description", target = "description", qualifiedByName = "trimToLongDescription")
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
    static String imageLinksMapToString(Map<String, String> imageLinks) {
        return imageLinks.get("smallThumbnail");
    }

    @Named("trimToLongDescription")
    static String trimToLongDescription(String description) {
        return description.length() < DESCRIPTION_MAX_LENGTH ? description : description.substring(0, DESCRIPTION_MAX_LENGTH) + "...";
    }
}
