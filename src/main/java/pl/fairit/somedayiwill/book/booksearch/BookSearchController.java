package pl.fairit.somedayiwill.book.booksearch;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.fairit.somedayiwill.book.usersbooks.Books;

@RestController
@RequestMapping("/books/search")
@Api(value = "Book searching")
public class BookSearchController {

    private final BookService bookService;

    public BookSearchController(final GoogleBooksService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "title")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Search for books by title", response = Books.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved books"),
    })
    public Books getBooksByTitle(@ApiParam(value = "title", required = true) @RequestParam(name = "title") final String title) {
        return bookService.searchBooksByTitle(title);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "author")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Search fot books by author", response = Books.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved books"),
    })
    public Books getBooksByAuthor(@ApiParam(value = "author", required = true) @RequestParam(name = "author") final String author) {
        return bookService.searchBooksByAuthor(author);
    }
}
