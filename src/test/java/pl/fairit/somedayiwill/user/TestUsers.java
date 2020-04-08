package pl.fairit.somedayiwill.user;

import com.github.javafaker.Faker;

import static java.util.Objects.nonNull;

public class TestUsers {
    public static AppUser aUserWithRandomCredentials() {
        var faker = new Faker();
        return AppUser.builder()
                .email(faker.number().numberBetween(0, 100) + faker.internet()
                        .emailAddress())
                .name(faker.name().username())
                .password(generateStrongPassword())
                .build();
    }

    public static AppUser aDefaultUser() {
        return AppUser.builder()
                .email("unique@email.com")
                .name("Sarah")
                .password("StrongPassword123")
                .build();
    }

    public static String generateStrongPassword() {
        var password = new Faker().internet().password(8, 16, true, false, true);
        if (isPasswordValid(password)) {
            return password;
        }
        return generateStrongPassword();
    }

    public static boolean isPasswordValid(String password) {
        return nonNull(password) && password.length() >= 8 &&
                password.chars().anyMatch(Character::isDigit) &&
                password.chars().anyMatch(Character::isLowerCase) &&
                password.chars().anyMatch(Character::isUpperCase);
    }
}
