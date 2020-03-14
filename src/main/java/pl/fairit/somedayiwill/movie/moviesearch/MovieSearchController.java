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

    private final MDBMovieService MDBMovieService;

    public MovieSearchController(final MDBMovieService MDBMovieService) {
        this.MDBMovieService = MDBMovieService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Search for movies that contain provided text", response = Movies.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved movies"),
    })
    public Movies getMoviesByQuery(@ApiParam(value = "q", required = true) @RequestParam(name = "q") final String query) {
        return MDBMovieService.searchMovies(query);
    }
}
