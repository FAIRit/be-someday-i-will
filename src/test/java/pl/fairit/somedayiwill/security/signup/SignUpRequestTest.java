package pl.fairit.somedayiwill.security.signup;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import pl.fairit.somedayiwill.security.user.SignUpRequest;

import static org.junit.jupiter.api.Assertions.*;

class SignUpRequestTest {
    @Test
    void shouldReturnFalseWhenPasswordIsTooShort() {
        var signupRequest = new SignUpRequest();
        var faker = new Faker();

        signupRequest.setPassword(faker.internet().password(0, 7));

        assertFalse(signupRequest.isPasswordValid());
    }

    @Test
    void shouldReturnFalseWhenPasswordIsAllLowerCase() {
        var faker = new Faker();
        var signupRequest = new SignUpRequest();

        signupRequest.setPassword(faker.internet().password(8, 100, false));

        assertFalse(signupRequest.isPasswordValid());
    }

    @Test
    void shouldReturnTrueWhenPasswordValid() {
        var faker = new Faker();
        var signupRequest = new SignUpRequest();

        signupRequest.setPassword(faker.internet().password(8, 100, true, true, true));

        assertTrue(signupRequest.isPasswordValid());
    }
}
