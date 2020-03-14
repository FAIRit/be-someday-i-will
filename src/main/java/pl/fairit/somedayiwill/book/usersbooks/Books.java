package pl.fairit.somedayiwill.book.usersbooks;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Books {
    private List<BookDto> books;
}
