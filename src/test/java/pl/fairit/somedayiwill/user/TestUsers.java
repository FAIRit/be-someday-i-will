package pl.fairit.somedayiwill.user;

import com.github.javafaker.Faker;
import pl.fairit.somedayiwill.newsletter.NewsletterFrequency;

public class TestUsers {
    public static AppUser aUserWithRandomCredentials() {
        var faker = new Faker();
        return AppUser.builder()
                .email(faker.internet().emailAddress())
                .name(faker.name().name())
                .password(faker.internet().password(8, 16, true, true, true))
                .build();
    }

    public static AppUser aDefaultUser() {
        return AppUser.builder()
                .email("unique@email.com")
                .name("Sarah")
                .password("StrongPassword123")
                .build();
    }

    public static AppUser aUserWithWeeklyNewsletterFrequency() {
        return AppUser.builder()
                .email("unique@email.com")
                .newsletterFrequency(NewsletterFrequency.WEEKLY)
                .name("Sarah")
                .password("StrongPassword123")
                .build();
    }

    public static AppUser aUserWithMonthlyNewsletterFrequency() {
        return AppUser.builder()
                .email("unique@email.com")
                .newsletterFrequency(NewsletterFrequency.MONTHLY)
                .name("Sarah")
                .password("StrongPassword123")
                .build();
    }
}
