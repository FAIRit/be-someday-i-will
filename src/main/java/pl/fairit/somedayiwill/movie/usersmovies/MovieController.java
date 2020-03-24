package pl.fairit.somedayiwill.movie.usersmovies;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.fairit.somedayiwill.security.user.CurrentUser;
import pl.fairit.somedayiwill.security.user.UserPrincipal;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/users/me/movies")
@Api(value = "Movie resource")
public class MovieController {
    private final MovieService movieService;

    public MovieController(final MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(value = "/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get one of your movies by its ID", response = MovieDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a movie"),
            @ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public MovieDto getMovieById(@ApiIgnore @CurrentUser final UserPrincipal userPrincipal,
                                 @ApiParam(value = "Movie's ID", required = true) @PathVariable(name = "movieId") final Long movieId) {
        return movieService.getUsersMovie(movieId, userPrincipal.getId());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get an object containing a list of all your movies", response = Movies.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved movies"),
            @ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Movies getAllMovies(@ApiIgnore @CurrentUser final UserPrincipal userPrincipal) {
        return movieService.getAllUsersMovies(userPrincipal.getId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Movies successfully uploaded"),
            @ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public MovieDto addMovie(@RequestBody final MovieDto movieDto, @ApiIgnore @CurrentUser final UserPrincipal userPrincipal) {
        return movieService.saveMovie(movieDto, userPrincipal.getId());
    }

    @DeleteMapping(value = "/{movieId}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete your movie by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Movie successfully deleted"),
            @ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public void deleteMovieById(@ApiIgnore @CurrentUser final UserPrincipal userPrincipal,
                                @ApiParam(value = "Movie's ID", required = true) @PathVariable(name = "movieId") final Long movieId) {
        movieService.deleteUsersMovie(movieId, userPrincipal.getId());
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete all of your movies")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Movies successfully deleted"),
            @ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public void deleteAllMovies(@ApiIgnore @CurrentUser final UserPrincipal userPrincipal) {
        movieService.deleteAllUsersMovies(userPrincipal.getId());
    }
}
