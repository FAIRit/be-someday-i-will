package pl.fairit.somedayiwill.movie.testmovies;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import pl.fairit.somedayiwill.movie.usersmovies.MovieDto;
import pl.fairit.somedayiwill.movie.usersmovies.Movies;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class TestMovies {
    public static Movies withListOfRandomMovies(final int listSize) {
        var movieDtoList = new ArrayList<MovieDto>();
        for (int i = 0; i < listSize; i++) {
            movieDtoList.add(TestMovieDto.aRandomMovieDto());
        }
        return new Movies(movieDtoList);
    }

    public static Movies fromJSONString(final String moviesAsString) {
        try {
            return new ObjectMapper().readValue(moviesAsString, Movies.class);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
