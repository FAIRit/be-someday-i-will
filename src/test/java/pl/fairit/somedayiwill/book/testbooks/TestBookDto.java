package pl.fairit.somedayiwill.book.testbooks;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import pl.fairit.somedayiwill.book.usersbooks.BookDto;

import java.io.IOException;

@Slf4j
public class TestBookDto {
    public static BookDto aRandomBookDto() {
        var faker = new Faker();
        return BookDto.builder()
                .title(faker.book().title())
                .subtitle(faker.book().title())
                .authors(faker.book().author())
                .categories(faker.book().genre())
                .description(faker.lorem().sentence())
                .pageCount(faker.number().numberBetween(10, 500))
                .imageLink(faker.internet().url())
                .buyLink(faker.internet().url())
                .build();
    }

    public static String asJSONString(final BookDto bookDto) {
        var bookAsJSONString = new JSONObject();
        try {
            bookAsJSONString.put("title", bookDto.getTitle());
            bookAsJSONString.put("subtitle", bookDto.getSubtitle());
            bookAsJSONString.put("authors", bookDto.getAuthors());
            bookAsJSONString.put("categories", bookDto.getCategories());
            bookAsJSONString.put("description", bookDto.getDescription());
            bookAsJSONString.put("pageCount", bookDto.getPageCount());
            bookAsJSONString.put("imageLink", bookDto.getImageLink());
            bookAsJSONString.put("buyLink", bookDto.getBuyLink());
            bookAsJSONString.put("id", bookDto.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bookAsJSONString.toString();
    }

    public static BookDto fromJSONString(final String bookAsJSONString) {
        try {
            return new ObjectMapper().readValue(bookAsJSONString, BookDto.class);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
