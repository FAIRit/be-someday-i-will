package pl.fairit.somedayiwill.book.usersbooks;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class BookDto {
    private Long id;
    private String description;
    private String title;
    private String subtitle;
    private String authors;
    private Integer pageCount;
    private String buyLink;
    private String imageLink;
    private String categories;
}
