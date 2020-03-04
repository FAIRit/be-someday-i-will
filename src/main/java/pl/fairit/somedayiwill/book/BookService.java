package pl.fairit.somedayiwill.book;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fairit.somedayiwill.exceptions.ResourceNotFoundException;
import pl.fairit.somedayiwill.user.AppUserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final AppUserService userService;

    public BookService(BookRepository bookRepository, AppUserService userService) {
        this.bookRepository = bookRepository;
        this.userService = userService;
    }

    public void saveBook(final Book book, final Long userId) {
        var user = userService.getExistingUser(userId);
        book.setUser(user);
        bookRepository.save(book);
    }

    public Books getAllUsersBooks(final Long userId) {
        List<BookDto> bookDtoList = bookRepository.findAllByUserId(userId).stream()
                .map(BookMapper.INSTANCE::map)
                .collect(Collectors.toList());
        return new Books(bookDtoList);
    }

    public Book getUsersBook(final Long bookId, final Long userId) {
        var existingBook = getExistingBookById(bookId);
        if (existingBook.getUser().getId().equals(userId)) {
            return existingBook;
        }
        throw new AccessDeniedException("You do not have permission to access this content");
    }

    public void deleteUsersBook(final Long bookId, final Long userId) {
        var existingBook = getExistingBookById(bookId);
        if (existingBook.getUser().getId().equals(userId)) {
            bookRepository.deleteById(bookId);
        }
        throw new AccessDeniedException("You do not have permission to access this content");
    }

    public void deleteAllUsersBooks(final Long userId) {
        bookRepository.deleteAllByUserId(userId);
    }

    private Book getExistingBookById(final Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book with given id does not exist"));
    }
}
