package pl.fairit.somedayiwill.movie.testmovies;

import com.github.javafaker.Faker;
import pl.fairit.somedayiwill.movie.usersmovies.Movie;

public class TestMovie {
    public static Movie aRandomMovie() {
        var faker = new Faker();
        return Movie.builder()
                .genres(faker.book().genre())
                .posterLink(faker.internet().url())
                .description(faker.lorem().sentence())
                .title(faker.book().title())
                .build();
    }
}
