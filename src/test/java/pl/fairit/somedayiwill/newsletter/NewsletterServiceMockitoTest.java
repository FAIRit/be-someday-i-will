package pl.fairit.somedayiwill.newsletter;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.TemplateEngine;
import pl.fairit.somedayiwill.book.usersbooks.BookService;
import pl.fairit.somedayiwill.movie.usersmovies.MovieService;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.user.AppUserService;

import java.util.Collections;
import java.util.List;

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

    //todo: check if weekly/monthly newsletter goes to proper users according to NewsletterFrequency they chose
    //todo: check if sendHTMLMail method from SendGridEmailService was called for every provided user
    //how to mock email template for process method (TextTemplateEngine)?

    @Test
    public void shouldSendNewsletterToAllAppUsers() {

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
