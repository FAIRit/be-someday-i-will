package pl.fairit.somedayiwill.movie.testdatabuilder;

import com.github.javafaker.Faker;
import pl.fairit.somedayiwill.movie.usersmovies.Movie;

import java.time.LocalDate;

public class TestMovie {

    public static Movie aRandomMovie() {
        var faker = new Faker();
        return Movie.builder()
                .genres(faker.book().genre())
                .posterLink(faker.internet().url())
                .releaseDate(LocalDate.now())
                .description(faker.lorem().sentence())
                .title(faker.book().title())
                .build();
    }
}
