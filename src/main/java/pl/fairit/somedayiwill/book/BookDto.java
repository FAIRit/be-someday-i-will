package pl.fairit.somedayiwill.book;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BookDto {
    private Long id;
    private String description;
    private String kind;
    private String title;
    private String subtitle;
    private String authors;
    private String pageCount;
    private String buyLink;
    private String imageLink;
}
