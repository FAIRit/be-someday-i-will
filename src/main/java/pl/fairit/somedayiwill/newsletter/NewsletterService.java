package pl.fairit.somedayiwill.newsletter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.fairit.somedayiwill.book.usersbooks.BookDto;
import pl.fairit.somedayiwill.book.usersbooks.BookService;
import pl.fairit.somedayiwill.movie.MovieDto;
import pl.fairit.somedayiwill.movie.MovieService;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.user.AppUserService;

import java.util.List;

@Slf4j
@Component
public class NewsletterService {

    private final SendGridEmailService sendGridEmailService;
    private final AppUserService appUserService;
    private final BookService bookService;
    private final MovieService movieService;

    public NewsletterService(SendGridEmailService sendGridEmailService, AppUserService appUserService, BookService bookService, MovieService movieService) {
        this.sendGridEmailService = sendGridEmailService;
        this.appUserService = appUserService;
        this.bookService = bookService;
        this.movieService = movieService;
    }

    @Scheduled(cron = "${app.cron.weekly-pattern}")
    private void sendWeeklyNewsletter() {
        log.info("Sending weekly newsletter");
        appUserService.getAllUsersForWeeklyNewsletter().forEach(this::sendEmail);
    }

    @Scheduled(cron = "${app.cron.monthly-pattern}")
    private void sendMonthlyNewsletter() {
        log.info("Sending monthly newsletter");
        appUserService.getAllUsersForMonthlyNewsletter().forEach(this::sendEmail);
    }

    public void sendEmail(final AppUser appUser) {
        var subject = "Your " + appUser.getNewsletterFrequency().name() + " newsletter";
        var movies = movieService.getAllUsersMovies(appUser.getId()).getMovies();
        var books = bookService.getAllUsersBooks(appUser.getId()).getBooks();
        var content = createMainContent(appUser.getName(), movies, books);
        sendGridEmailService.sendMail(appUser.getEmail(), subject, content);
    }

    //todo: thymeleaf html draft
    private String createMainContent(final String name, final List<MovieDto> movies, final List<BookDto> books) {
        var stringBuffer = new StringBuffer();
        stringBuffer.append("Hi ").append(name).append("!");
        if (movies.size() == 0 && books.size() == 0) {
            stringBuffer.append("You didn't add any book or movie to your lists. Try our movie browser and book browser!");
        }
        if (movies.size() > 0) {
            stringBuffer.append("You have ").append(movies.size()).append(" movies on your watch later list! Check them out!\n");
            movies.forEach(movie ->
                    stringBuffer.append(movie.getTitle()).append(" ").append(movie.getGenres()).append("\n"));
        }
        if (books.size() > 0) {
            stringBuffer.append("You have ").append(books.size()).append(" books on your read later list! Check them out!\n");
            books.forEach(book ->
                    stringBuffer.append(book.getTitle()).append(" ").append(book.getAuthors()).append(" ").append(book.getCategories()).append("\n"));
        }
        return stringBuffer.toString();
    }
}
