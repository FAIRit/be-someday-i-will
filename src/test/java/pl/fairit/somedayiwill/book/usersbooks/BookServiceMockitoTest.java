package pl.fairit.somedayiwill.book.usersbooks;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import pl.fairit.somedayiwill.exceptions.ResourceNotFoundException;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.user.AppUserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceMockitoTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    AppUserService userService;

    @InjectMocks
    BookService bookService;

    @Test
    public void shouldDeleteAllUsersBooksWhenUserIdGiven() {
        var userId = 5L;

        bookService.deleteAllUsersBooks(userId);

        verify(bookRepository, times(1)).deleteAllByUserId(userId);
    }

    @Test
    public void shouldDeleteUsersBookWhenUserIdAndBookIdGiven() {
        var appUser = retrieveAppUser();
        var book = retrieveOneBook();
        book.setId(13L);
        book.setUser(appUser);

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        bookService.deleteUsersBook(book.getId(), appUser.getId());

        verify(bookRepository, times(1)).deleteById(book.getId());
    }

    @Test
    public void shouldThrowAccessDeniedExceptionWhenGivenUserIdDoesNotMatchBookOwnerId() {
        var book = retrieveOneBook();
        book.setId(12L);
        book.setUser(retrieveAppUser());
        var userId = 23L;

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        assertThrows(AccessDeniedException.class, () -> bookService.getUsersBook(book.getId(), userId));
    }

    @Test
    public void shouldReturnUsersBooksWhenUserWithGivenIdExist() {
        var booksToReturn = retrieveBooks();
        var booksToReturnByRepository = booksToReturn.getBooks().stream()
                .map(BookMapper.INSTANCE::mapBookDtoToBook)
                .collect(Collectors.toList());
        var userId = 3L;

        when(bookRepository.findAllByUserId(userId)).thenReturn(booksToReturnByRepository);

        var result = bookService.getAllUsersBooks(userId);
        assertEquals(booksToReturn, result);
    }

    @Test
    public void shouldSaveBookWhenUserWithGivenIdExists() {
        var user = retrieveAppUser();
        var bookDtoToSave = retrieveOneBookDto();

        when(userService.getExistingUser(user.getId())).thenReturn(user);

        bookService.saveBook(bookDtoToSave, user.getId());
        verify(bookRepository, times(1)).save(ArgumentMatchers.any());
    }

    //fixme: access to getExistingBookById() -> it should be private, now its package-private
    @Test
    public void shouldThrowResourceNotFoundExceptionWhenBookWithGivenIdDoesNotExist() {
        var bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.getExistingBookById(bookId));
    }

    private Books retrieveBooks() {
        return new Books(List.of(retrieveOneBookDto(), retrieveOneBookDto(), retrieveOneBookDto()));
    }

    private BookDto retrieveOneBookDto() {
        var faker = new Faker();
        return BookDto.builder()
                .authors(faker.book().author())
                .buyLink("https://buyMe.com")
                .categories(faker.book().genre())
                .description(faker.lorem().sentence())
                .imageLink("https://lookAtMe.com")
                .pageCount(345)
                .title(faker.book().title())
                .build();
    }

    private Book retrieveOneBook() {
        var faker = new Faker();
        return Book.builder()
                .authors(faker.book().author())
                .buyLink("https://buyMe.com")
                .categories(faker.book().genre())
                .description(faker.lorem().sentence())
                .imageLink("https://lookAtMe.com")
                .pageCount(345)
                .title(faker.book().title())
                .build();
    }

    private AppUser retrieveAppUser() {
        return AppUser.builder()
                .id(5L)
                .build();
    }
}
