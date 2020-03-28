package pl.fairit.somedayiwill.book.testbooks;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import pl.fairit.somedayiwill.book.usersbooks.BookDto;
import pl.fairit.somedayiwill.book.usersbooks.Books;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class TestBooks {
    public static Books withListOfRandomBooks(int listSize) {
        var bookDtoList = new ArrayList<BookDto>();
        for (int i = 0; i < listSize; i++) {
            bookDtoList.add(TestBookDto.aRandomBookDto());
        }
        return new Books(bookDtoList);
    }

    public static Books fromJSONString(String booksAsString) {
        try {
            return new ObjectMapper().readValue(booksAsString, Books.class);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
