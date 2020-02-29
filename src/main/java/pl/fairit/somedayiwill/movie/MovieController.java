package pl.fairit.somedayiwill.movie;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.user.CurrentUser;
import pl.fairit.somedayiwill.user.UserPrincipal;
import pl.fairit.somedayiwill.user.UserService;

@RestController
@RequestMapping("/users/me/movies")
public class MovieController {
    private final MovieService movieService;
    private final UserService userService;

    public MovieController(MovieService movieService, UserService userService) {
        this.movieService = movieService;
        this.userService = userService;
    }

    @GetMapping("/{movieId}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public Movie getMovieById(@CurrentUser UserPrincipal userPrincipal, @PathVariable(name = "movieId") final Long movieId) {
        return movieService.findExistingMovieById(movieId);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public Movies getAllMovies(@CurrentUser UserPrincipal userPrincipal) {
        return movieService.findAllMoviesByUserId(userPrincipal.getId());
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMovie(@RequestBody final Movie movie, @CurrentUser UserPrincipal userPrincipal) {
        final AppUser existingUser = userService.findExistingUser(userPrincipal.getId());
        movieService.saveMovie(movie, existingUser);
    }

    @DeleteMapping("/delete/{movieId}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovieById(@CurrentUser UserPrincipal userPrincipal, @PathVariable(name = "movieId") final Long movieId) {
        movieService.deleteById(movieId);
    }

    @DeleteMapping("/delete/all")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllMovies(@CurrentUser UserPrincipal userPrincipal) {
        movieService.deleteAllMovies(userPrincipal.getId());
    }
}
