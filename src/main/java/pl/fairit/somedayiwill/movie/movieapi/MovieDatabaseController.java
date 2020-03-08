package pl.fairit.somedayiwill.movie.movieapi;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.fairit.somedayiwill.movie.usersmovies.Movies;

@RestController
@RequestMapping("/movies/search")
@Api(value = "Movie searching")
public class MovieDatabaseController {

    private final MovieDatabaseService movieDatabaseService;

    public MovieDatabaseController(MovieDatabaseService movieDatabaseService) {
        this.movieDatabaseService = movieDatabaseService;
    }

    @GetMapping(value = "/{query}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Search for movies that contain provided text", response = Movies.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved movies"),
    })
    public Movies getMoviesByQuery(@ApiParam(value = "query", required = true) @PathVariable(name = "query") final String query) {
        return movieDatabaseService.searchMovies(query);
    }
}
