package pl.fairit.somedayiwill.book.testbooks;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import pl.fairit.somedayiwill.book.usersbooks.Books;

import java.io.IOException;

@Slf4j
public class TestBooks {
    public static Books fromJSONString(String moviesAsString) {
        try {
            return new ObjectMapper().readValue(moviesAsString, Books.class);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
