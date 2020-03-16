package pl.fairit.somedayiwill.movie.moviesearch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.fairit.somedayiwill.movie.usersmovies.MovieDto;
import pl.fairit.somedayiwill.movie.usersmovies.MovieMapper;
import pl.fairit.somedayiwill.movie.usersmovies.Movies;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class MDBMovieService implements MovieService {
    @Value("${app.movie-database.base-url}")
    private String movieApiBaseUrl;

    @Value("${app.movie-database.key}")
    private String movieApiKey;

    private final RestTemplate restTemplate;

    public MDBMovieService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Movies searchMovies(final String query) {
        var fullPath = getFullPath(query);
        var apiResponse = restTemplate.getForEntity(fullPath, MDBWrapper.class);
        if (isNull(apiResponse.getBody())) {
            return new Movies(Collections.emptyList());
        }
        var mdbWrapper = (MDBWrapper) apiResponse.getBody();
        return new Movies(mapResponseBodyToMovieDtoList(mdbWrapper));
    }

    private Map<Integer, String> getGenresMap() {
        var apiResponse = restTemplate.getForEntity(getGenresPath(), Genres.class);
        if (isNull(apiResponse.getBody())) {
            return Collections.emptyMap();
        }
        var genresWrapper = (Genres) apiResponse.getBody();
        return Arrays.stream(genresWrapper.getGenres())
                .collect(Collectors.toMap(Genre::getId, Genre::getName));
    }

    private List<MovieDto> mapResponseBodyToMovieDtoList(final MDBWrapper wrapper) {
        var genresMap = getGenresMap();
        return Arrays.stream(wrapper.getResults())
                .map(mdbMovie -> MovieMapper.INSTANCE.mapMDBMovieToMovieDto(mdbMovie, genresMap))
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


}
