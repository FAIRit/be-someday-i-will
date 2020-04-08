package pl.fairit.somedayiwill.movie.testmovies;

import com.github.javafaker.Faker;
import pl.fairit.somedayiwill.movie.usersmovies.Movie;
import pl.fairit.somedayiwill.user.AppUser;

public class TestMovie {
    private final static Faker FAKER = new Faker();


    public static Movie aRandomMovie() {
        return Movie.builder()
                .genres(FAKER.book()
                        .genre())
                .posterLink(FAKER.internet()
                        .url())
                .description(FAKER.lorem()
                        .sentence())
                .title(FAKER.book()
                        .title())
                .build();
    }

    public static Movie aRandomMovie(AppUser user) {
        return Movie.builder()
                .user(user)
                .genres(FAKER.book()
                        .genre())
                .posterLink(FAKER.internet()
                        .url())
                .description(FAKER.lorem()
                        .sentence())
                .title(FAKER.book()
                        .title())
                .build();
    }
}
