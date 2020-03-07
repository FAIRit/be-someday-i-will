package pl.fairit.somedayiwill.book;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import pl.fairit.somedayiwill.googlebooksapi.VolumeInfo;

import java.util.Map;
import java.util.StringJoiner;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDto mapBookToBookDto(Book book);

    Book mapBookDtoToBook(BookDto bookDto);

    @Mapping(source = "imageLinks", target = "imageLink", qualifiedByName = "imageLinksMapToString")
    @Mapping(source = "authors", target = "authors", qualifiedByName = "stringArrayToString")
    @Mapping(source = "categories", target = "categories", qualifiedByName = "stringArrayToString")
    BookDto mapVolumeInfoToBookDto(VolumeInfo volumeInfo);

    @Named("stringArrayToString")
    static String stringArrayToString(String[] array) {
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
}
