package pl.fairit.somedayiwill.movie.usersmovies;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import pl.fairit.somedayiwill.exceptions.ResourceNotFoundException;
import pl.fairit.somedayiwill.movie.testmovies.TestMovie;
import pl.fairit.somedayiwill.movie.testmovies.TestMovieDto;
import pl.fairit.somedayiwill.movie.testmovies.TestMovies;
import pl.fairit.somedayiwill.user.AppUserService;
import pl.fairit.somedayiwill.user.TestUsers;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    @Mock
    MovieRepository movieRepository;

    @Mock
    AppUserService userService;

    @InjectMocks
    MovieService movieService;

    @Test
    public void shouldDeleteAllUsersMoviesWhenUserIdGiven() {
        var userId = 5L;

        movieService.deleteAllUsersMovies(userId);

        verify(movieRepository, times(1)).deleteAllByUserId(userId);
    }

    @Test
    public void shouldDeleteUsersMovieWhenUserIdAndMovieIdGiven() {
        var appUser = TestUsers.aDefaultUser();
        appUser.setId(5L);
        var movie = TestMovie.aRandomMovie();
        movie.setId(13L);
        movie.setUser(appUser);

        when(movieRepository.findById(movie.getId())).thenReturn(Optional.of(movie));
        movieService.deleteUsersMovie(movie.getId(), appUser.getId());

        verify(movieRepository, times(1)).deleteById(movie.getId());
    }

    @Test
    public void shouldThrowAccessDeniedExceptionWhenGivenUserIdDoesNotMatchMovieOwnerId() {
        var movie = TestMovie.aRandomMovie();
        var appUser = TestUsers.aDefaultUser();
        appUser.setId(13L);
        var wrongUserId = 23L;
        movie.setId(12L);
        movie.setUser(appUser);

        when(movieRepository.findById(movie.getId())).thenReturn(Optional.of(movie));

        assertThrows(AccessDeniedException.class, () -> movieService.getUsersMovie(movie.getId(), wrongUserId));
    }

    @Test
    public void shouldReturnUsersMoviesWhenUserWithGivenIdExist() {
        var moviesToReturn = TestMovies.withListOfRandomMovies(3);
        var moviesToReturnByRepository = moviesToReturn.getMovies().stream()
                .map(MovieMapper.INSTANCE::mapMovieDtoToMovie)
                .collect(Collectors.toList());
        var userId = 3L;

        when(movieRepository.findAllByUserId(userId)).thenReturn(moviesToReturnByRepository);
        var result = movieService.getAllUsersMovies(userId);

        assertEquals(moviesToReturn, result);
    }

    @Test
    public void shouldSaveMovieWhenUserWithGivenIdExists() {
        var user = TestUsers.aDefaultUser();
        var movieDtoToSave = TestMovieDto.aRandomMovieDto();

        when(userService.getExistingUser(user.getId())).thenReturn(user);
        movieService.saveMovie(movieDtoToSave, user.getId());

        verify(movieRepository, times(1)).save(ArgumentMatchers.any());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenMovieWithGivenIdDoesNotExist() {
        var movieId = 1L;

        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> movieService.getExistingMovieById(movieId));
    }
}
