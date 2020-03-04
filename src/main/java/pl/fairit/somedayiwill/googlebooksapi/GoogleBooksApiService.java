package pl.fairit.somedayiwill.googlebooksapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleBooksApiService {
    @Value("${app.google-books.base-url}")
    private String bookApiBaseUrl;

    @Value("${app.google-books.api-key}")
    private String googleApiKey;

    private final RestTemplate restTemplate;

    public GoogleBooksApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public VolumeInfo getBookDetails() {
        return restTemplate.getForObject(bookApiBaseUrl, VolumeInfo.class);
    }
}
