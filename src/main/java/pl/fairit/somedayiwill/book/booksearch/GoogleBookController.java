package pl.fairit.somedayiwill.book.booksearch;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.fairit.somedayiwill.book.usersbooks.Books;

@RestController
@RequestMapping("/books/search")
@Api(value = "Book searching")
public class GoogleBookController {

    private final GoogleBooksService googleBooksService;

    public GoogleBookController(final GoogleBooksService googleBooksService) {
        this.googleBooksService = googleBooksService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Search for books that contain provided text", response = Books.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved books"),
    })
    public Books getBooksByQuery(@ApiParam(value = "q", required = true) @RequestParam(name = "q") final String query) {
        return googleBooksService.searchBooks(query);
    }
}
