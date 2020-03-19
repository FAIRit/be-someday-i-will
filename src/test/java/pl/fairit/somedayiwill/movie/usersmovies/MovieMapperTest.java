package pl.fairit.somedayiwill.movie.usersmovies;

import org.junit.jupiter.api.Test;
import pl.fairit.somedayiwill.movie.testdatabuilder.TestMDBMovie;
import pl.fairit.somedayiwill.movie.testdatabuilder.TestMovie;
import pl.fairit.somedayiwill.movie.testdatabuilder.TestMovieDto;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class MovieMapperTest {

    @Test
    void shouldMapMDBMovieToMovieDto() {
        var mdbMovie = TestMDBMovie.aRandomMDBMovie();
        var genres = new HashMap<Integer, String>();
        genres.put(1, "Adventure");
        genres.put(5, "Sci-Fi");
        genres.put(13, "Animation");

        var movieDto = MovieMapper.INSTANCE.mapMDBMovieToMovieDto(mdbMovie, genres);

        assertThat(movieDto.getGenres()).isEqualTo("Adventure, Animation");
    }

    @Test
    void shouldMapMovieToMovieDto() {
        var movie = TestMovie.aRandomMovie();

        var movieDto = MovieMapper.INSTANCE.mapMovieToMovieDto(movie);

        assertThat(movieDto.getGenres()).isEqualTo(movie.getGenres());
        assertThat(movieDto.getDescription()).isEqualTo(movie.getDescription());
        assertThat(movieDto.getPosterLink()).isEqualTo(movie.getPosterLink());
        assertThat(movieDto.getReleaseDate()).isEqualTo(movie.getReleaseDate());
        assertThat(movieDto.getTitle()).isEqualTo(movie.getTitle());
    }

    @Test
    void shouldMapMovieDtoToMovie() {
        var movieDto = TestMovieDto.aRandomMovieDto();

        var movie = MovieMapper.INSTANCE.mapMovieDtoToMovie(movieDto);

        assertThat(movie.getGenres()).isEqualTo(movieDto.getGenres());
        assertThat(movie.getDescription()).isEqualTo(movieDto.getDescription());
        assertThat(movie.getPosterLink()).isEqualTo(movieDto.getPosterLink());
        assertThat(movie.getReleaseDate()).isEqualTo(movieDto.getReleaseDate());
        assertThat(movie.getTitle()).isEqualTo(movieDto.getTitle());
    }
}
