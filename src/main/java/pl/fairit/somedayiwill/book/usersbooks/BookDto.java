package pl.fairit.somedayiwill.book.usersbooks;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "Book details")
public class BookDto {
    @ApiModelProperty(notes = "The database generated book ID")
    private Long id;

    @ApiModelProperty(notes = "The book description")
    private String description;

    @ApiModelProperty(notes = "The title of the book")
    private String title;

    @ApiModelProperty(notes = "The subtitle of the book")
    private String subtitle;

    @ApiModelProperty(notes = "The authors of the book")
    private String authors;

    @ApiModelProperty(notes = "The number of pages")
    private Integer pageCount;

    @ApiModelProperty(notes = "The link to book in Google Book store")
    private String buyLink;

    @ApiModelProperty(notes = "The cover image URL")
    private String imageLink;

    @ApiModelProperty(notes = "The genres of the book")
    private String categories;
}
