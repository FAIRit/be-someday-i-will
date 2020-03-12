package pl.fairit.somedayiwill.security.signup;

import org.junit.jupiter.api.Test;
import pl.fairit.somedayiwill.security.user.SignUpRequest;

import static org.junit.jupiter.api.Assertions.*;

class SignUpRequestTest {
    @Test
    void shouldReturnFalseWhenPasswordIsTooShort() {
        var signupRequest = new SignUpRequest();

        signupRequest.setPassword("Bad1");

        assertFalse(signupRequest.isPasswordValid());
    }

    @Test
    void shouldReturnFalseWhenPasswordIsAllLowerCase() {
        var signupRequest = new SignUpRequest();

        signupRequest.setPassword("badpassword1");

        assertFalse(signupRequest.isPasswordValid());
    }

    @Test
    void shouldReturnTrueWhenPasswordValid() {
        var signupRequest = new SignUpRequest();

        signupRequest.setPassword("CorrectPassword1");

        assertTrue(signupRequest.isPasswordValid());
    }
}
