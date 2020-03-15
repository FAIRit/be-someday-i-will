package pl.fairit.somedayiwill.newsletter;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import pl.fairit.somedayiwill.book.usersbooks.BookService;
import pl.fairit.somedayiwill.book.usersbooks.Books;
import pl.fairit.somedayiwill.movie.usersmovies.MovieDto;
import pl.fairit.somedayiwill.movie.usersmovies.MovieService;
import pl.fairit.somedayiwill.movie.usersmovies.Movies;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.user.AppUserService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsletterServiceMockitoTest {

    @Mock
    SendGridEmailService emailService;

    @Mock
    AppUserService userService;

    @Mock
    BookService bookService;

    @Mock
    MovieService movieService;

    @Mock
    TemplateEngine templateEngine;

    @InjectMocks
    NewsletterService newsletterService;

    @Test
    public void shouldSendNewsletterToAllAppUsers() {
//        var faker = new Faker();
//        var users = List.of(retrieveAppUser());
//
//        when(userService.getAllUsersForWeeklyNewsletter()).thenReturn(users);
//
//        newsletterService.sendWeeklyNewsletter();
//        verify(newsletterService, times(users.size())).sendNewsletter(users.get(0));
    }

    private List<AppUser> retrieveAppUserList() {
        return List.of(retrieveAppUser(), retrieveAppUser(), retrieveAppUser());
    }

    private AppUser retrieveAppUser() {
        var faker = new Faker();
        return AppUser.builder()
                .newsletterFrequency(NewsletterFrequency.WEEKLY)
                .name(faker.name().name())
                .books(Collections.emptyList())
                .movies(Collections.emptyList())
                .email(faker.internet().emailAddress())
                .id(5L)
                .build();
    }
}
