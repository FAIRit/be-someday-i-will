package pl.fairit.somedayiwill.book.googlebooksapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.fairit.somedayiwill.book.usersbooks.Book;
import pl.fairit.somedayiwill.book.usersbooks.BookDto;
import pl.fairit.somedayiwill.book.usersbooks.BookMapper;
import pl.fairit.somedayiwill.book.usersbooks.Books;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Component
public class GoogleBooksService {
    @Value("${app.google-books.base-url}")
    private String bookApiBaseUrl;

    @Value("${app.google-books.key}")
    private String googleApiKey;

    private final RestTemplate restTemplate;

    public GoogleBooksService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Books searchBooks(final String query) {
        var fullPath = getFullPath(query);
        ResponseEntity apiResponse = restTemplate.getForEntity(fullPath, GBWrapper.class);
        if (isNull(apiResponse.getBody())) return new Books(Collections.emptyList());
        var gbWrapper = (GBWrapper) apiResponse.getBody();
        return new Books(mapResponseBodyToBookDtoList(gbWrapper));
    }

    private List<BookDto> mapResponseBodyToBookDtoList(final GBWrapper wrapper) {
        return Arrays.stream(wrapper.getItems())
                .map(GBItemsWrapper::getVolumeInfo)
                .map(BookMapper.INSTANCE::mapVolumeInfoToBookDto)
                .collect(Collectors.toList());
    }

    private String getFullPath(final String query) {
        var fullPath = new StringBuffer();
        fullPath.append(bookApiBaseUrl)
                .append("/volumes?q=")
                .append(query.replaceAll(" ", "%20"))
                .append("&key=")
                .append(googleApiKey);
        return fullPath.toString();
    }
}
