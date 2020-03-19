package pl.fairit.somedayiwill.movie.testdatabuilder;

import pl.fairit.somedayiwill.movie.usersmovies.MovieDto;
import pl.fairit.somedayiwill.movie.usersmovies.Movies;

import java.util.List;

public class MoviesBuilder {

    private List<MovieDto> movieDtoList;

    private MoviesBuilder() {
    }

    public static MoviesBuilder movies() {
        return new MoviesBuilder();
    }

    public MoviesBuilder withOneRandomMovie() {
        this.movieDtoList = List.of(MovieDtoBuilder.aMovieDto().build());
        return this;
    }

    public MoviesBuilder withListOfRandomMovies(int listSize) {
        for (int i = 0; i < listSize; i++) {
            movieDtoList.add(MovieDtoBuilder.aMovieDto().build());
        }
        return this;
    }

    public Movies build() {
        return new Movies(movieDtoList);
    }
}
