package pl.fairit.somedayiwill.movie.testdatabuilder;

import pl.fairit.somedayiwill.movie.usersmovies.MovieDto;
import pl.fairit.somedayiwill.movie.usersmovies.Movies;

import java.util.ArrayList;
import java.util.List;

public class TestMovies {
    public static Movies withOneRandomMovie() {
        return new Movies(List.of(TestMovieDto.aRandomMovieDto()));
    }

    public static Movies withListOfRandomMovies(int listSize) {
        var movieDtoList = new ArrayList<MovieDto>();
        for (int i = 0; i < listSize; i++) {
            movieDtoList.add(TestMovieDto.aRandomMovieDto());
        }
        return new Movies(movieDtoList);
    }
}
