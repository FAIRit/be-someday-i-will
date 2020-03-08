package pl.fairit.somedayiwill.movie.movieapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.fairit.somedayiwill.movie.usersmovies.MovieDto;
import pl.fairit.somedayiwill.movie.usersmovies.MovieMapper;
import pl.fairit.somedayiwill.movie.usersmovies.Movies;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class MovieDatabaseService {
    @Value("${app.movie-database.base-url}")
    private String movieApiBaseUrl;

    @Value("${app.movie-database.poster-base-url}")
    private String posterBaseUrl;

    @Value("${app.movie-database.key}")
    private String movieApiKey;

    private final RestTemplate restTemplate;

    public MovieDatabaseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Movies searchMovies(final String query) {
        var fullPath = getFullPath(query);
        ResponseEntity apiResponse = restTemplate.getForEntity(fullPath, MDBWrapper.class);
        if (isNull(apiResponse.getBody())) return new Movies(Collections.emptyList());
        var mdbWrapper = (MDBWrapper) apiResponse.getBody();
        return new Movies(mapResponseBodyToMovieDtoList(mdbWrapper));
    }

    private Map<Integer, String> getGenresMap() {
        ResponseEntity apiResponse = restTemplate.getForEntity(getGenresPath(), GenresWrapper.class);
        if (isNull(apiResponse.getBody())) return Collections.emptyMap();
        var genresWrapper = (GenresWrapper) apiResponse.getBody();
        return Arrays.stream(genresWrapper.getGenres())
                .collect(Collectors.toMap(Genre::getId, Genre::getName));
    }

    private List<MovieDto> mapResponseBodyToMovieDtoList(final MDBWrapper wrapper) {
        var genresMap = getGenresMap();
        return Arrays.stream(wrapper.getResults())
                .map(mdbMovie -> mapGenresIdsToGenres(mdbMovie, genresMap))
                .map(this::mapPosterPathWithFullLink)
                .map(MovieMapper.INSTANCE::mapMDBMovieToMovieDto)
                .collect(Collectors.toList());
    }

    private String getFullPath(final String query) {
        var fullPath = new StringBuffer();
        fullPath.append(movieApiBaseUrl)
                .append("/search/movie?api_key=")
                .append(movieApiKey)
                .append("&query=")
                .append(query.replaceAll(" ", "%20"));
        return fullPath.toString();
    }

    private String getGenresPath() {
        var fullGenresPath = new StringBuffer();
        fullGenresPath.append(movieApiBaseUrl)
                .append("/genre/movie/list?api_key=")
                .append(movieApiKey);
        return fullGenresPath.toString();
    }

    private String getFullPosterLink(final String posterPath) {
        return isNull(posterPath) ? "" : posterBaseUrl + posterPath;
    }

    private MDBMovie mapPosterPathWithFullLink(MDBMovie mdbMovie) {
        mdbMovie.setPoster_path(getFullPosterLink(mdbMovie.getPoster_path()));
        return mdbMovie;
    }

    private MDBMovie mapGenresIdsToGenres(MDBMovie mdbMovie, Map<Integer, String> genresMap) {
        List<String> genreList = new ArrayList<>();
        Arrays.stream(mdbMovie.getGenre_ids())
                .forEach(genreId -> genreList.add(genresMap.get(genreId)));
        mdbMovie.setGenres(genreList);
        return mdbMovie;
    }
}
