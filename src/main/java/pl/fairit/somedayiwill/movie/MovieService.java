package pl.fairit.somedayiwill.movie;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fairit.somedayiwill.exceptions.ResourceNotFoundException;
import pl.fairit.somedayiwill.user.AppUser;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    public Movie saveMovie(final Movie movie, final AppUser user) {
        movie.setUser(user);
        return movieRepository.save(movie);
    }

    public Movies findAllMoviesByUserId(final Long userId) {
        List<MovieDto> movieDtoList = movieRepository.findAllByUserId(userId).stream()
                .map(MovieMapper.INSTANCE::movieToMovieDto)
                .collect(Collectors.toList());
        return new Movies(movieDtoList);
    }

    private Optional<Movie> findMovieById(final Long movieId) {
        return movieRepository.findById(movieId);
    }

    public Movie findExistingMovieById(final Long movieId) {
        return findMovieById(movieId).orElseThrow(ResourceNotFoundException::new);
    }

    public void deleteById(final Long movieId) {
        movieRepository.deleteById(movieId);
    }

    public void deleteAllMovies(final Long userId) {
        movieRepository.deleteAllByUserId(userId);
    }
}
