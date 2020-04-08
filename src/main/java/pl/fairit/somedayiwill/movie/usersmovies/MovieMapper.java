package pl.fairit.somedayiwill.movie.usersmovies;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import pl.fairit.somedayiwill.movie.moviesearch.MDBMovie;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Mapper
public interface MovieMapper {
    String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w154";
    int DESCRIPTION_MAX_LENGTH = 997;

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    MovieDto mapMovieToMovieDto(Movie movie);

    @Mapping(source = "description", target = "description", qualifiedByName = "trimToLongDescription")
    Movie mapMovieDtoToMovie(MovieDto movieDto);

    default MovieDto mapMDBMovieToMovieDto(MDBMovie mdbMovie, Map<Integer, String> genresMap) {
        if (mdbMovie == null) {
            return null;
        }

        var genres = Arrays.stream(mdbMovie.getGenre_ids())
                .map(genresMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));

        MovieDto movieDto = new MovieDto();
        movieDto.setGenres(genres);
        movieDto.setDescription(trimToLongDescription(mdbMovie.getOverview()));
        movieDto.setPosterLink(getFullPosterLink(mdbMovie.getPoster_path()));
        movieDto.setTitle(mdbMovie.getTitle());

        return movieDto;
    }

    private String getFullPosterLink(final String posterPath) {
        return isNull(posterPath) ? "" : POSTER_BASE_URL + posterPath;
    }

    @Named("trimToLongDescription")
    static String trimToLongDescription(String description) {
        if (isNull(description)) {
            return "";
        }
        return description.length() < DESCRIPTION_MAX_LENGTH ? description : description.substring(0, DESCRIPTION_MAX_LENGTH) + "...";
    }
}
