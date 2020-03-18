package pl.fairit.somedayiwill.book.usersbooks;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@ApiModel
public class Books {
    @ApiModelProperty(notes = "The list of books")
    private List<BookDto> books;
}
