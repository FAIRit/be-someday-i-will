package pl.fairit.somedayiwill.book.booksearch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.fairit.somedayiwill.book.usersbooks.BookDto;
import pl.fairit.somedayiwill.book.usersbooks.BookMapper;
import pl.fairit.somedayiwill.book.usersbooks.Books;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
public class GoogleBooksService implements BookService {
    private static final String SEARCH_BY_AUTHOR_KEYWORD = "inauthor:";
    private static final String SEARCH_BY_TITLE_KEYWORD = "intitle:";
    private final RestTemplate restTemplate;
    @Value("${app.google-books.base-url}")
    private String bookApiBaseUrl;
    @Value("${app.google-books.key}")
    private String googleApiKey;

    public GoogleBooksService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Books searchBooksByTitle(final String title) {
        return searchBooks(getFullPath(title, SEARCH_BY_TITLE_KEYWORD));
    }

    @Override
    public Books searchBooksByAuthor(final String author) {
        return searchBooks(getFullPath(author, SEARCH_BY_AUTHOR_KEYWORD));
    }

    public Books searchBooks(final String fullPath) {
        var apiResponse = restTemplate.getForEntity(fullPath, GBooks.class);
        final List<BookDto> wrapper = nonNull(apiResponse.getBody()) ? mapResponseBodyToBookDtoList(apiResponse.getBody()) : Collections.emptyList();
        return new Books(wrapper);
    }

    private List<BookDto> mapResponseBodyToBookDtoList(final GBooks wrapper) {
        return Arrays.stream(wrapper.getItems())
                .map(GBookWrapper::getVolumeInfo)
                .map(BookMapper.INSTANCE::mapGBookToBookDto)
                .collect(Collectors.toList());
    }

    private String getFullPath(final String query, final String searchKeyword) {
        var fullPath = new StringBuilder();
        fullPath.append(bookApiBaseUrl)
                .append("/volumes?q=")
                .append(searchKeyword)
                .append(query.replace(" ", "+"))
                .append("&key=")
                .append(googleApiKey);
        return fullPath.toString();
    }
}
