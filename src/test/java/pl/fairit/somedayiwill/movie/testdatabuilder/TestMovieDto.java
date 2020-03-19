package pl.fairit.somedayiwill.movie.testdatabuilder;

import com.github.javafaker.Faker;
import pl.fairit.somedayiwill.movie.usersmovies.MovieDto;

import java.time.LocalDate;

public class TestMovieDto {
    public static MovieDto aRandomMovieDto() {
        var faker = new Faker();
        return MovieDto.builder()
                .genres(faker.book().genre())
                .posterLink(faker.internet().url())
                .releaseDate(LocalDate.now())
                .description(faker.lorem().sentence())
                .title(faker.book().title())
                .build();
    }
}
