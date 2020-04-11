package pl.fairit.somedayiwill.book.usersbooks;

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

    public BookService(final BookRepository bookRepository, final AppUserService userService) {
        this.bookRepository = bookRepository;
        this.userService = userService;
    }

    public BookDto saveBook(final BookDto bookDto, final Long userId) {
        var user = userService.getExistingUser(userId);
        var bookToSave = BookMapper.INSTANCE.mapBookDtoToBook(bookDto);
        bookToSave.setUser(user);
        bookRepository.save(bookToSave);
        return BookMapper.INSTANCE.mapBookToBookDto(bookToSave);
    }

    public Books getAllUsersBooks(final Long userId) {
        List<BookDto> bookDtoList = bookRepository.findAllByUserId(userId).stream()
                .map(BookMapper.INSTANCE::mapBookToBookDto)
                .collect(Collectors.toList());
        return new Books(bookDtoList);
    }

    public BookDto getUsersBook(final Long bookId, final Long userId) {
        var existingBook = getExistingBookById(bookId);
        if (existingBook.getUser().getId().equals(userId)) {
            return BookMapper.INSTANCE.mapBookToBookDto(existingBook);
        }
        throw new AccessDeniedException("You do not have permission to access this content");
    }

    public void deleteUsersBook(final Long bookId, final Long userId) {
        var existingBook = getExistingBookById(bookId);
        if (!existingBook.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You do not have permission to access this content");
        }
        bookRepository.deleteById(bookId);
    }

    public void deleteAllUsersBooks(final Long userId) {
        bookRepository.deleteAllByUserId(userId);
    }

    Book getExistingBookById(final Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book with given id does not exist"));
    }
}
