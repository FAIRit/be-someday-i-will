package pl.fairit.somedayiwill.movie.testmovies;

import com.github.javafaker.Faker;
import pl.fairit.somedayiwill.movie.moviesearch.MDBMovie;
import pl.fairit.somedayiwill.movie.moviesearch.MDBWrapper;
import pl.fairit.somedayiwill.movie.usersmovies.MovieMapper;
import pl.fairit.somedayiwill.movie.usersmovies.Movies;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TestMDBMovie {
    public static MDBMovie aRandomMDBMovie() {
        var faker = new Faker();
        return MDBMovie.builder()
                .genre_ids(new Integer[]{1, 6, 13})
                .poster_path(faker.internet().url())
                .overview(faker.gameOfThrones().quote())
                .title(faker.harryPotter().house())
                .build();
    }

    public static Movies toMovieDto(final MDBWrapper moviesToReturn) {
        MDBMovie[] results = moviesToReturn.getResults();
        var genres = TestGenres.asMap();
        return new Movies(Arrays.stream(results)
                .map(mdbMovie -> MovieMapper.INSTANCE.mapMDBMovieToMovieDto(mdbMovie, genres))
                .collect(Collectors.toList()));
    }
}
