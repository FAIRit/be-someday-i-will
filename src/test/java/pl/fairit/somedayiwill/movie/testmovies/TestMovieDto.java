package pl.fairit.somedayiwill.movie.testmovies;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import pl.fairit.somedayiwill.movie.usersmovies.MovieDto;

import java.io.IOException;

@Slf4j
public class TestMovieDto {
    public static MovieDto aRandomMovieDto() {
        var faker = new Faker();
        return MovieDto.builder()
                .genres(faker.book().genre())
                .posterLink(faker.internet().url())
                .description(faker.lorem().sentence())
                .title(faker.book().title())
                .build();
    }

    public static String asJSONString(MovieDto movieDto) {
        var movieAsJSONString = new JSONObject();
        try {
            movieAsJSONString.put("title", movieDto.getTitle());
            movieAsJSONString.put("description", movieDto.getDescription());
            movieAsJSONString.put("genres", movieDto.getGenres());
            movieAsJSONString.put("posterLink", movieDto.getPosterLink());
            movieAsJSONString.put("id", movieDto.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieAsJSONString.toString();
    }

    public static MovieDto fromJSONString(String movieAsJSONString) {
        try {
            return new ObjectMapper().readValue(movieAsJSONString, MovieDto.class);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
