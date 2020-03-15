package pl.fairit.somedayiwill.movie.moviesearch;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.fairit.somedayiwill.movie.usersmovies.MovieMapper;
import pl.fairit.somedayiwill.movie.usersmovies.Movies;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MDBMovieServiceMockitoTest {
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    MDBMovieService movieService;

    @Test
    void shouldReturnMoviesWhenQueryGiven() {
        var query = "frozen";
        var moviesToReturn = retrieveMDBMovies();
        var movies = mapMDBMoviesToMovieDto(moviesToReturn);

        when(restTemplate.getForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.eq(MDBWrapper.class))).thenReturn(new ResponseEntity<>(moviesToReturn, HttpStatus.OK));
        when(restTemplate.getForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.eq(Genres.class))).thenReturn(new ResponseEntity<>(retrieveGenres(), HttpStatus.OK));
        var foundMovies = movieService.searchMovies(query);

        assertEquals(foundMovies, movies);
    }

    private Movies mapMDBMoviesToMovieDto(MDBWrapper moviesToReturn) {
        MDBMovie[] results = moviesToReturn.getResults();
        var genres = retrieveGenresMap();
        return new Movies(Arrays.stream(results)
                .map(mdbMovie -> MovieMapper.INSTANCE.mapMDBMovieToMovieDto(mdbMovie, genres))
                .collect(Collectors.toList()));
    }

    private MDBWrapper retrieveMDBMovies() {
        var results = new MDBMovie[]{retrieveOneMDBMovie(), retrieveOneMDBMovie(), retrieveOneMDBMovie()};
        var totalResults = results.length;
        return new MDBWrapper(totalResults, results);
    }

    private MDBMovie retrieveOneMDBMovie() {
        var faker = new Faker();
        return MDBMovie.builder()
                .genre_ids(new Integer[]{12, 35})
                .poster_path(faker.internet().url())
                .overview(faker.lorem().sentence())
                .title(faker.book().title())
                .build();
    }

    private Genres retrieveGenres() {
        return new Genres(new Genre[]{new Genre(28, "Action"), new Genre(12, "Adventure"), new Genre(16, "Animation"), new Genre(35, "Comedy")});
    }

    private Map<Integer, String> retrieveGenresMap() {
        var genresMap = new HashMap<Integer, String>();
        genresMap.put(28, "Action");
        genresMap.put(12, "Adventure");
        genresMap.put(16, "Animation");
        genresMap.put(35, "Comedy");
        return genresMap;
    }


}