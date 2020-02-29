package pl.fairit.somedayiwill.user;

import lombok.Builder;
import lombok.Data;
import pl.fairit.somedayiwill.book.Book;
import pl.fairit.somedayiwill.movie.Movie;

import java.util.List;

@Builder
@Data
public class UserDto {
    private String name;
    private String email;
    private List<Book> books;
    private List<Movie> movies;
}
