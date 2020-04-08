package pl.fairit.somedayiwill.movie.moviesearch;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.fairit.somedayiwill.movie.usersmovies.Movies;

@RestController
@RequestMapping("/movies/search")
@Api(value = "Movie searching")
public class MovieSearchController {

    private final MovieService movieService;

    public MovieSearchController(final MDBMovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "title")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Search for movies by title", response = Movies.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved movies"),
    })
    public Movies getMoviesByTitle(@ApiParam(value = "title", required = true) @RequestParam(name = "title") final String query) {
        return movieService.searchMoviesByTitle(query);
    }
}
