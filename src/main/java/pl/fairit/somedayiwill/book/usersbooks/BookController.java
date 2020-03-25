package pl.fairit.somedayiwill.book.usersbooks;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.fairit.somedayiwill.movie.usersmovies.MovieDto;
import pl.fairit.somedayiwill.security.user.CurrentUser;
import pl.fairit.somedayiwill.security.user.UserPrincipal;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/users/me/books")
@Api(value = "Book resource")
public class BookController {

    private final BookService bookService;

    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get one of your books by its ID", response = BookDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a book"),
            @ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public BookDto getBookById(@ApiParam(value = "Credentials injected while user has valid token", required = true) @ApiIgnore @CurrentUser final UserPrincipal userPrincipal,
                               @ApiParam(value = "Book's ID", required = true) @PathVariable(name = "bookId") final Long bookId) {
        return bookService.getUsersBook(bookId, userPrincipal.getId());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get an object containing a list of all your books", response = Books.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved books"),
            @ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Books getAllBooks(@ApiIgnore @CurrentUser final UserPrincipal userPrincipal) {
        return bookService.getAllUsersBooks(userPrincipal.getId());
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add a book to your watchlist", response = BookDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Book successfully uploaded"),
            @ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public BookDto addBook(@ApiParam(value = "Book to add", required = true) @RequestBody final BookDto bookDto, @ApiIgnore @CurrentUser final UserPrincipal userPrincipal) {
        return bookService.saveBook(bookDto, userPrincipal.getId());
    }

    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete your book by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Book successfully deleted"),
            @ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public void deleteBookById(@ApiIgnore @CurrentUser final UserPrincipal userPrincipal,
                               @ApiParam(value = "Book's ID", required = true) @PathVariable(name = "bookId") final Long bookId) {
        bookService.deleteUsersBook(bookId, userPrincipal.getId());
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete all of your books")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Books successfully deleted"),
            @ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public void deleteAllBooks(@ApiIgnore @CurrentUser final UserPrincipal userPrincipal) {
        bookService.deleteAllUsersBooks(userPrincipal.getId());
    }
}
