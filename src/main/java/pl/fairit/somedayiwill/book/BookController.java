package pl.fairit.somedayiwill.book;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.user.CurrentUser;
import pl.fairit.somedayiwill.user.UserPrincipal;
import pl.fairit.somedayiwill.user.UserService;

@RestController
@RequestMapping("/users/me/books")
public class BookController {

    private final BookService bookService;
    private final UserService userService;

    public BookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping("/{bookId}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public Book getBookById(@CurrentUser UserPrincipal userPrincipal, @PathVariable(name = "bookId") final Long bookId) {
        return bookService.findExistingBookById(bookId);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public Books getAllBooks(@CurrentUser UserPrincipal userPrincipal) {
        return bookService.findAllBooksByUserId(userPrincipal.getId());
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(@RequestBody final Book book, @CurrentUser UserPrincipal userPrincipal) {
        final AppUser existingUser = userService.findExistingUser(userPrincipal.getId());
        bookService.saveBook(book, existingUser);
    }

    @DeleteMapping("/delete/{bookId}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookById(@CurrentUser UserPrincipal userPrincipal, @PathVariable(name = "bookId") final Long bookId) {
        bookService.deleteById(bookId);
    }

    @DeleteMapping("/delete/all")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllBooks(@CurrentUser UserPrincipal userPrincipal) {
        bookService.deleteAllBooks(userPrincipal.getId());
    }
}
