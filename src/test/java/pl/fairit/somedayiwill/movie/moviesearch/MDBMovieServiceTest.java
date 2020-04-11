package pl.fairit.somedayiwill.movie.moviesearch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.fairit.somedayiwill.movie.testmovies.TestGenres;
import pl.fairit.somedayiwill.movie.testmovies.TestMDBMovie;
import pl.fairit.somedayiwill.movie.testmovies.TestMDBWrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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
        var movies = TestMDBMovie.toMovieDto(moviesToReturn);

        when(restTemplate.getForEntity(anyString(), eq(MDBWrapper.class)))
                .thenReturn(new ResponseEntity<>(moviesToReturn, HttpStatus.OK));
        when(restTemplate.getForEntity(anyString(), eq(Genres.class)))
                .thenReturn(new ResponseEntity<>(TestGenres.genres(), HttpStatus.OK));
        var foundMovies = movieService.searchMoviesByTitle(query);

        assertEquals(foundMovies, movies);
    }
}
