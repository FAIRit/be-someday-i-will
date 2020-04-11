package pl.fairit.somedayiwill.book.usersbooks;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import pl.fairit.somedayiwill.book.booksearch.GBook;

import java.util.Map;

import static java.util.Objects.isNull;

@Mapper
public interface BookMapper {
    int DESCRIPTION_MAX_LENGTH = 997;

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Named("stringArrayToString")
    static String stringArrayToString(final String[] array) {
        return isNull(array) ? "" : String.join(", ", array);
    }

    @Named("imageLinksMapToString")
    static String imageLinksMapToString(final Map<String, String> imageLinks) {
        return imageLinks.get("smallThumbnail");
    }

    @Named("trimToLongDescription")
    static String trimToLongDescription(final String description) {
        if (isNull(description)) {
            return "";
        }
        return description.length() < DESCRIPTION_MAX_LENGTH ? description : description.substring(0, DESCRIPTION_MAX_LENGTH) + "...";
    }

    BookDto mapBookToBookDto(final Book book);

    @Mapping(source = "description", target = "description", qualifiedByName = "trimToLongDescription")
    Book mapBookDtoToBook(final BookDto bookDto);

    @Mapping(source = "imageLinks", target = "imageLink", qualifiedByName = "imageLinksMapToString")
    @Mapping(source = "authors", target = "authors", qualifiedByName = "stringArrayToString")
    @Mapping(source = "categories", target = "categories", qualifiedByName = "stringArrayToString")
    @Mapping(source = "description", target = "description", qualifiedByName = "trimToLongDescription")
    BookDto mapGBookToBookDto(final GBook gBook);
}
