package pl.fairit.somedayiwill.movie.testmovies;

import com.github.javafaker.Faker;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import pl.fairit.somedayiwill.movie.usersmovies.Movie;
import pl.fairit.somedayiwill.user.AppUser;

import java.time.LocalDate;

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

    public static Movie aRandomMovie(AppUser user) {
        var faker = new Faker();
        return Movie.builder()
                .user(user)
                .genres(faker.book().genre())
                .posterLink(faker.internet().url())
                .description(faker.lorem().sentence())
                .title(faker.book().title())
                .build();
    }
}
