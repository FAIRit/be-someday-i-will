package pl.fairit.somedayiwill.mailsender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.fairit.somedayiwill.book.BookDto;
import pl.fairit.somedayiwill.book.BookService;
import pl.fairit.somedayiwill.movie.MovieDto;
import pl.fairit.somedayiwill.movie.MovieService;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.user.UserService;

import java.util.List;

@Slf4j
@Component
public class NewsletterService {

    private final SendGridEmailService sendGridEmailService;
    private final UserService userService;
    private final BookService bookService;
    private final MovieService movieService;

    public NewsletterService(SendGridEmailService sendGridEmailService, UserService userService, BookService bookService, MovieService movieService) {
        this.sendGridEmailService = sendGridEmailService;
        this.userService = userService;
        this.bookService = bookService;
        this.movieService = movieService;
    }

    @Scheduled(cron = "0 0 8 1 * *")
//    @Scheduled(cron = "0 20 * * * *")
    private void sendWeeklyNewsletter() {
        log.info("Sending weekly newsletter");
        userService.findAllUsersForWeeklyNewsletter().forEach(this::sendEmail);
    }

    @Scheduled(cron = "0 0 8 * 1 *")
    private void sendMonthlyNewsletter() {
        log.info("Sending monthly newsletter");
        userService.findAllUsersForMonthlyNewsletter().forEach(this::sendEmail);
    }

    public void sendEmail(final AppUser appUser) {
        final String subject = "Your " + appUser.getNewsletterFrequency().name() + " newsletter";
        final List<MovieDto> movies = movieService.findAllMoviesByUserId(appUser.getId()).getMovies();
        final List<BookDto> books = bookService.findAllBooksByUserId(appUser.getId()).getBooks();
        final String content = createMainContent(appUser.getName(), movies, books);
        sendGridEmailService.sendMail(appUser.getEmail(), subject, content);
    }

    //fixme: add singular forms
    private String createMainContent(final String name, final List<MovieDto> movies, final List<BookDto> books) {
        final StringBuffer stringBuffer = new StringBuffer();
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
