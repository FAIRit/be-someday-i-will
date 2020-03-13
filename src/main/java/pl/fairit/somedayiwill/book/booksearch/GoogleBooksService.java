package pl.fairit.somedayiwill.book.booksearch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.fairit.somedayiwill.book.usersbooks.BookDto;
import pl.fairit.somedayiwill.book.usersbooks.BookMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
class GoogleBooksService implements BookService {
    @Value("${app.google-books.base-url}")
    private String bookApiBaseUrl;

    @Value("${app.google-books.key}")
    private String googleApiKey;

    private final RestTemplate restTemplate;

    public GoogleBooksService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public pl.fairit.somedayiwill.book.usersbooks.Books searchBooks(final String query) {
        var fullPath = getFullPath(query);
        ResponseEntity apiResponse = restTemplate.getForEntity(fullPath, GBooks.class);
        if (isNull(apiResponse.getBody()))
            return new pl.fairit.somedayiwill.book.usersbooks.Books(Collections.emptyList());
        var gbWrapper = (GBooks) apiResponse.getBody();
        return new pl.fairit.somedayiwill.book.usersbooks.Books(mapResponseBodyToBookDtoList(gbWrapper));
    }

    private List<BookDto> mapResponseBodyToBookDtoList(final GBooks wrapper) {
        return Arrays.stream(wrapper.getItems())
                .map(GBookWrapper::getVolumeInfo)
                .map(BookMapper.INSTANCE::mapGBookToBookDto)
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
