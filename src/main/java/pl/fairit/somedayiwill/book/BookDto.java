package pl.fairit.somedayiwill.book;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class BookDto {
    private Long id;
    private String description;
    @NotNull
    private String title;
    private String subtitle;
    private String authors;
    private int pageCount;
    private String buyLink;
    private String imageLink;
    private String categories;
}
