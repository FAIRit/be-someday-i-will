package pl.fairit.somedayiwill.movie;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.fairit.somedayiwill.security.user.CurrentUser;
import pl.fairit.somedayiwill.security.user.UserPrincipal;

@RestController
@RequestMapping("/users/me/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{movieId}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public Movie getMovieById(@CurrentUser UserPrincipal userPrincipal, @PathVariable(name = "movieId") final Long movieId) {
        return movieService.getUsersMovie(movieId, userPrincipal.getId());
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public Movies getAllMovies(@CurrentUser UserPrincipal userPrincipal) {
        return movieService.getAllUsersMovies(userPrincipal.getId());
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMovie(@RequestBody final Movie movie, @CurrentUser UserPrincipal userPrincipal) {
        movieService.saveMovie(movie, userPrincipal.getId());
    }

    @DeleteMapping("/{movieId}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovieById(@CurrentUser UserPrincipal userPrincipal, @PathVariable(name = "movieId") final Long movieId) {
        movieService.deleteUsersMovie(movieId, userPrincipal.getId());
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllMovies(@CurrentUser UserPrincipal userPrincipal) {
        movieService.deleteAllUsersMovies(userPrincipal.getId());
    }
}
