package pl.fairit.somedayiwill.movie.moviesearch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.fairit.somedayiwill.movie.testmovies.TestGenres;
import pl.fairit.somedayiwill.movie.testmovies.TestMDBWrapper;
import pl.fairit.somedayiwill.movie.usersmovies.MovieMapper;
import pl.fairit.somedayiwill.movie.usersmovies.Movies;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MDBMovieServiceTest {
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    MDBMovieService movieService;

    @Test
    void shouldReturnMoviesWhenQueryGiven() {
        var query = "frozen";
        var moviesToReturn = TestMDBWrapper.aWrapperWithMultipleMDBMovies(3);
        var movies = mapMDBMoviesToMovieDto(moviesToReturn);

        when(restTemplate.getForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.eq(MDBWrapper.class))).thenReturn(new ResponseEntity<>(moviesToReturn, HttpStatus.OK));
        when(restTemplate.getForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.eq(Genres.class))).thenReturn(new ResponseEntity<>(TestGenres.genres(), HttpStatus.OK));
        var foundMovies = movieService.searchMoviesByTitle(query);

        assertEquals(foundMovies, movies);
    }

    private Movies mapMDBMoviesToMovieDto(MDBWrapper moviesToReturn) {
        MDBMovie[] results = moviesToReturn.getResults();
        var genres = TestGenres.asMap();
        return new Movies(Arrays.stream(results)
                .map(mdbMovie -> MovieMapper.INSTANCE.mapMDBMovieToMovieDto(mdbMovie, genres))
                .collect(Collectors.toList()));
    }
}
