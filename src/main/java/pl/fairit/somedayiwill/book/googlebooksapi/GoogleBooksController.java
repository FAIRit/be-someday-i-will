package pl.fairit.somedayiwill.book.googlebooksapi;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.fairit.somedayiwill.book.usersbooks.BookDto;
import pl.fairit.somedayiwill.book.usersbooks.Books;

@RestController
@RequestMapping("/books/search")
@Api(value = "Book searching")
public class GoogleBooksController {

    private final GoogleBooksService googleBooksService;

    public GoogleBooksController(GoogleBooksService googleBooksService) {
        this.googleBooksService = googleBooksService;
    }

    @GetMapping(value = "/{query}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Search for volumes that contain provided text", response = BookDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved books"),
    })
    public Books getBooksByQuery(@ApiParam(value = "query", required = true) @PathVariable(name = "query") final String query) {
        return googleBooksService.searchBooks(query);
    }
}
