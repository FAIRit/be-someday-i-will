package pl.fairit.somedayiwill.book;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fairit.somedayiwill.exceptions.ResourceNotFoundException;
import pl.fairit.somedayiwill.user.AppUser;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

//    public Books finAllBooksByUserId(final Long userId) {
//        return new Books(bookRepository.findAllByUserId(userId));
//    }

    public Book saveBook(final Book book, final AppUser user) {
        book.setUser(user);
        return bookRepository.save(book);
    }

    public Books finAllBooksByUserId(final Long userId) {
        List<BookDto> bookDtoList = bookRepository.findAllByUserId(userId).stream()
                .map(BookMapper.INSTANCE::bookToBookDto)
                .collect(Collectors.toList());
        return new Books(bookDtoList);
    }

    private Optional<Book> findBookById(final Long bookId) {
        return bookRepository.findById(bookId);
    }

    public Book findExistingBookById(final Long bookId) {
        return findBookById(bookId).orElseThrow(ResourceNotFoundException::new);
    }

    public void deleteById(final Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public void deleteAllBooks(final Long userId) {
        bookRepository.deleteAllByUserId(userId);
    }
}
