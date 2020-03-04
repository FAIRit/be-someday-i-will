package pl.fairit.somedayiwill.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.fairit.somedayiwill.book.Book;
import pl.fairit.somedayiwill.movie.Movie;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {
    private String name;
    private String email;
}
