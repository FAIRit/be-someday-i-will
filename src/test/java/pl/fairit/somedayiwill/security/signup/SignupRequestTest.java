package pl.fairit.somedayiwill.security.signup;

import com.github.javafaker.Faker;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.fairit.somedayiwill.security.TestAuthorization;
import pl.fairit.somedayiwill.security.user.SignupRequest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.fairit.somedayiwill.user.TestUsers.generateStrongPassword;

class SignupRequestTest {
    private static Stream<Arguments> signupRequests() {
        return Stream.of(
                Arguments.of(TestAuthorization.aSignUpRequest("strongForSure124!"), true),
                Arguments.of(TestAuthorization.aSignUpRequest(generateStrongPassword()), true),
                Arguments.of(TestAuthorization.aSignUpRequest(new Faker().internet()
                        .password(8, 16, false)), false),
                Arguments.of(TestAuthorization.aSignUpRequest(new Faker().internet()
                        .password(0, 7)), false)
        );
    }

    @ParameterizedTest
    @MethodSource("signupRequests")
    void validatePasswords(SignupRequest request, Boolean isPasswordValid) {
        assertEquals(isPasswordValid, request.isPasswordValid());
    }
}
