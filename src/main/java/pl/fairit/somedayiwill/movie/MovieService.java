package pl.fairit.somedayiwill.movie;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fairit.somedayiwill.exceptions.ResourceNotFoundException;
import pl.fairit.somedayiwill.user.AppUserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovieService {
    private final MovieRepository movieRepository;
    private final AppUserService userService;

    public MovieService(MovieRepository movieRepository, AppUserService userService) {
        this.movieRepository = movieRepository;
        this.userService = userService;
    }

    public void saveMovie(final Movie movie, final Long userId) {
        var user = userService.getExistingUser(userId);
        movie.setUser(user);
        movieRepository.save(movie);
    }

    public Movies getAllUsersMovies(final Long userId) {
        List<MovieDto> movieDtoList = movieRepository.findAllByUserId(userId).stream()
                .map(MovieMapper.INSTANCE::map)
                .collect(Collectors.toList());
        return new Movies(movieDtoList);
    }

    public Movie getUsersMovie(final Long movieId, final Long userId) {
        var existingMovie = getExistingMovieById(movieId);
        if (existingMovie.getUser().getId().equals(userId)) {
            return existingMovie;
        }
        throw new AccessDeniedException("You do not have permission to access this content");
    }

    public void deleteUsersMovie(final Long movieId, final Long userId) {
        var existingMovie = getExistingMovieById(movieId);
        if (existingMovie.getUser().getId().equals(userId)) {
            movieRepository.deleteById(movieId);
        }
        throw new AccessDeniedException("You do not have permission to access this content");
    }

    public void deleteAllUsersMovies(final Long userId) {
        movieRepository.deleteAllByUserId(userId);
    }

    private Movie getExistingMovieById(final Long movieId) {
        return movieRepository.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("Movie with given id does not exist"));
    }
}
