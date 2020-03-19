package pl.fairit.somedayiwill.movie.usersmovies;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import pl.fairit.somedayiwill.movie.moviesearch.MDBMovie;

import java.time.LocalDate;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class MovieMapperTest {

    @Test
    void shouldMapMDBMovieToMovieDto() {
        var faker = new Faker();
        var mdbMovie = MDBMovie.builder()
                .genre_ids(new Integer[]{1, 6, 13})
                .poster_path(faker.internet().url())
                .release_date(LocalDate.now())
                .overview(faker.gameOfThrones().quote())
                .title(faker.harryPotter().house())
                .build();
        var genres = new HashMap<Integer, String>();
        genres.put(1, "Adventure");
        genres.put(5, "Sci-Fi");
        genres.put(13, "Animation");

        var movieDto = MovieMapper.INSTANCE.mapMDBMovieToMovieDto(mdbMovie, genres);

        assertThat(movieDto.getGenres()).isEqualTo("Adventure, Animation");
    }

    @Test
    void shouldMapMovieToMovieDto() {
        var faker = new Faker();
        var movie = Movie.builder()
                .genres("Adventure, Animation")
                .posterLink(faker.internet().url())
                .releaseDate(LocalDate.now())
                .description(faker.lorem().sentence())
                .title(faker.book().title())
                .build();

        var movieDto = MovieMapper.INSTANCE.mapMovieToMovieDto(movie);

        assertThat(movieDto.getGenres()).isEqualTo(movie.getGenres());
        assertThat(movieDto.getDescription()).isEqualTo(movie.getDescription());
        assertThat(movieDto.getPosterLink()).isEqualTo(movie.getPosterLink());
        assertThat(movieDto.getReleaseDate()).isEqualTo(movie.getReleaseDate());
        assertThat(movieDto.getTitle()).isEqualTo(movie.getTitle());
    }

    @Test
    void shouldMapMovieDtoToMovie() {
        var faker = new Faker();
        var movieDto = MovieDto.builder()
                .genres(faker.book().genre())
                .posterLink(faker.internet().url())
                .releaseDate(LocalDate.now())
                .description(faker.lorem().sentence())
                .title(faker.book().title())
                .build();

        var movie = MovieMapper.INSTANCE.mapMovieDtoToMovie(movieDto);

        assertThat(movie.getGenres()).isEqualTo(movieDto.getGenres());
        assertThat(movie.getDescription()).isEqualTo(movieDto.getDescription());
        assertThat(movie.getPosterLink()).isEqualTo(movieDto.getPosterLink());
        assertThat(movie.getReleaseDate()).isEqualTo(movieDto.getReleaseDate());
        assertThat(movie.getTitle()).isEqualTo(movieDto.getTitle());
    }
}
