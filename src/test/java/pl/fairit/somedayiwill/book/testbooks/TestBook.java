package pl.fairit.somedayiwill.book.testbooks;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import pl.fairit.somedayiwill.book.usersbooks.Book;

@Slf4j
public class TestBook {
    public static Book aRandomBook() {
        var faker = new Faker();
        return Book.builder()
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
}
