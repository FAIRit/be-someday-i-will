package pl.fairit.somedayiwill.movie.usersmovies;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import pl.fairit.somedayiwill.movie.moviesearch.MDBMovie;

import java.util.Arrays;
import java.util.Map;
import java.util.StringJoiner;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Mapper
public interface MovieMapper {
    String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w154";

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    MovieDto mapMovieToMovieDto(Movie movie);

    Movie mapMovieDtoToMovie(MovieDto movieDto);

    default MovieDto mapMDBMovieToMovieDto(MDBMovie mdbMovie, Map<Integer, String> genresMap) {
        if (mdbMovie == null) {
            return null;
        }

        var joiner = new StringJoiner(", ");
        Arrays.stream(mdbMovie.getGenre_ids())
                .forEach(genreId -> {
                    var genre = genresMap.get(genreId);
                    if (nonNull(genre)) joiner.add(genre);
                });

        MovieDto movieDto = new MovieDto();
        movieDto.setGenres(joiner.toString());
        movieDto.setDescription(mdbMovie.getOverview());
        movieDto.setPosterLink(getFullPosterLink(mdbMovie.getPoster_path()));
        movieDto.setTitle(mdbMovie.getTitle());

        return movieDto;
    }

    private String getFullPosterLink(final String posterPath) {
        return isNull(posterPath) ? "" : POSTER_BASE_URL + posterPath;
    }
}
