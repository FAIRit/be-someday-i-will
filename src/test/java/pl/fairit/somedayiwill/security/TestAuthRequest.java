package pl.fairit.somedayiwill.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import pl.fairit.somedayiwill.security.jwt.AuthResponse;
import pl.fairit.somedayiwill.user.AppUser;

import java.io.IOException;

import static java.util.Objects.nonNull;

@Slf4j
public class TestAuthRequest {
    public static String retrieveLoginRequestBodyFromProvidedAppUser(final AppUser userToLogin) {
        var loginRequestBody = new JSONObject();
        try {
            loginRequestBody.put("email", userToLogin.getEmail());
            loginRequestBody.put("password", userToLogin.getPassword());
        } catch (JSONException e) {
            log.error(e.getMessage());
        }
        return loginRequestBody.toString();
    }

    public static String retrieveSignupRequestBodyFromProvidedAppUser(final AppUser userToRegister) {
        var signupRequestBody = new JSONObject();
        try {
            signupRequestBody.put("email", userToRegister.getEmail());
            signupRequestBody.put("name", userToRegister.getName());
            signupRequestBody.put("password", userToRegister.getPassword());
            if (nonNull(userToRegister.getNewsletterFrequency())) {
                signupRequestBody.put("newsletter_frequency", userToRegister.getNewsletterFrequency());
            }
        } catch (JSONException e) {
            log.error(e.getMessage());
        }
        return signupRequestBody.toString();
    }

    public static AuthResponse getTokenFromJSONString(String responseBody) {
        try {
            return new ObjectMapper().readValue(responseBody, AuthResponse.class);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
