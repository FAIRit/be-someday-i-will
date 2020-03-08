package pl.fairit.somedayiwill.newsletter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.BackOff;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.fairit.somedayiwill.book.usersbooks.BookDto;
import pl.fairit.somedayiwill.book.usersbooks.BookService;
import pl.fairit.somedayiwill.movie.usersmovies.MovieDto;
import pl.fairit.somedayiwill.movie.usersmovies.MovieService;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.user.AppUserService;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Slf4j
@Component
public class NewsletterService {
    private static final String NEWSLETTER_EMAIL_TEMPLATE = "newsletter-email.html";

    private final SendGridEmailService sendGridEmailService;
    private final AppUserService appUserService;
    private final BookService bookService;
    private final MovieService movieService;
    private final TemplateEngine textTemplateEngine;

    public NewsletterService(SendGridEmailService sendGridEmailService, AppUserService appUserService, BookService bookService, MovieService movieService, TemplateEngine textTemplateEngine) {
        this.sendGridEmailService = sendGridEmailService;
        this.appUserService = appUserService;
        this.bookService = bookService;
        this.movieService = movieService;
        this.textTemplateEngine = textTemplateEngine;
    }

    @Scheduled(cron = "${app.cron.weekly-pattern}")
    private void sendWeeklyNewsletter() {
        log.info("Sending weekly newsletter");
        appUserService.getAllUsersForWeeklyNewsletter().forEach(this::sendNewsletter);
    }

    @Scheduled(cron = "${app.cron.monthly-pattern}")
    private void sendMonthlyNewsletter() {
        log.info("Sending monthly newsletter");
        appUserService.getAllUsersForMonthlyNewsletter().forEach(this::sendNewsletter);
    }

    public void sendNewsletter(final AppUser appUser) {
        var frequency = appUser.getNewsletterFrequency().getValue();
        var subject = "Your " + frequency + " newsletter";
        var movies = movieService.getAllUsersMovies(appUser.getId()).getMovies();
        var books = bookService.getAllUsersBooks(appUser.getId()).getBooks();
        var content = createNewsletterHtmlContent(appUser.getName(), movies, books, frequency);
        var email = appUser.getEmail();
        sendGridEmailService.sendHtmlMail(content, email, subject);
    }

    private String createNewsletterHtmlContent(final String name, final List<MovieDto> movies, final List<BookDto> books, final String frequency) {
        final Locale locale = new Locale("en");
        final Context ctx = new Context(locale);
        ctx.setVariable("frequency", frequency);
        ctx.setVariable("name", name);
        ctx.setVariable("books", books);
        ctx.setVariable("movies", movies);
        return textTemplateEngine.process(NEWSLETTER_EMAIL_TEMPLATE, ctx);
    }
}
