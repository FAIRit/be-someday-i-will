package pl.fairit.somedayiwill.movie.testmovies;

import com.github.javafaker.Faker;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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

    public static String asJSONString(MovieDto movieDto) {
        var movieAsJSONString = new JSONObject();
        try {
            movieAsJSONString.put("title", movieDto.getTitle());
            movieAsJSONString.put("description", movieDto.getDescription());
            movieAsJSONString.put("release_date", movieDto.getReleaseDate());
            movieAsJSONString.put("genres", movieDto.getGenres());
            movieAsJSONString.put("poster_link", movieDto.getPosterLink());
            movieAsJSONString.put("id", movieDto.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieAsJSONString.toString();
    }
}
