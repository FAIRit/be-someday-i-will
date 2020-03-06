package pl.fairit.somedayiwill.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.fairit.somedayiwill.security.user.CurrentUser;
import pl.fairit.somedayiwill.security.user.UserPrincipal;

@RestController
@RequestMapping("/users/me/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public Book getBookById(@CurrentUser UserPrincipal userPrincipal, @PathVariable(name = "bookId") final Long bookId) {
        return bookService.getUsersBook(bookId, userPrincipal.getId());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public Books getAllBooks(@CurrentUser UserPrincipal userPrincipal) {
        return bookService.getAllUsersBooks(userPrincipal.getId());
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(@RequestBody final Book book, @CurrentUser UserPrincipal userPrincipal) {
        bookService.saveBook(book, userPrincipal.getId());
    }

    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookById(@CurrentUser UserPrincipal userPrincipal, @PathVariable(name = "bookId") final Long bookId) {
        bookService.deleteUsersBook(bookId, userPrincipal.getId());
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllBooks(@CurrentUser UserPrincipal userPrincipal) {
        bookService.deleteAllUsersBooks(userPrincipal.getId());
    }
}
