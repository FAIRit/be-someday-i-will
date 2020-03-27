package pl.fairit.somedayiwill.book.testbooks;

import com.github.javafaker.Faker;
import pl.fairit.somedayiwill.book.booksearch.GBook;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class TestGBook {
    public static GBook aRandomGBook() {
        var faker = new Faker();
        return GBook.builder()
                .authors(new String[]{faker.book().author(), faker.book().author()})
                .buyLink(faker.internet().url())
                .categories(new String[]{faker.book().genre()})
                .description(faker.lorem().sentence())
                .imageLinks(Collections.singletonMap("smallThumbnail", faker.internet().url()))
                .pageCount(faker.number().numberBetween(10, 300))
                .publishedDate((faker.date().past(2010, TimeUnit.DAYS)).toString())
                .title(faker.book().title())
                .publisher(faker.book().publisher())
                .build();
    }
}
