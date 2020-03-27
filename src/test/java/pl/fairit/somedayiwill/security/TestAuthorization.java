package pl.fairit.somedayiwill.security;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import pl.fairit.somedayiwill.security.jwt.AuthResponse;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.user.TestUsers;

import java.io.IOException;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static java.util.Objects.nonNull;

@Slf4j
public class TestAuthorization {
    public static String getToken(int port) {
        RestAssured.port = port;
        var user = TestUsers.aUserWithRandomCredentials();
        var signupRequest = aSignupRequest(user);
        var loginRequest = aLoginRequest(user);

        given()
                .port(port)
                .basePath("/")
                .contentType(ContentType.JSON)
                .body(signupRequest)
                .post("/auth/signup");

        var authResponse = given()
                .port(port)
                .basePath("/")
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .post("/auth/login")
                .then()
                .extract()
                .body()
                .asString();

        return Objects.requireNonNull(getTokenFromJSONString(authResponse)).getAccessToken();
    }

    private static String aLoginRequest(final AppUser userToLogin) {
        var loginRequestBody = new JSONObject();
        try {
            loginRequestBody.put("email", userToLogin.getEmail());
            loginRequestBody.put("password", userToLogin.getPassword());
        } catch (JSONException e) {
            log.error(e.getMessage());
        }
        return loginRequestBody.toString();
    }

    private static String aSignupRequest(final AppUser userToRegister) {
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

    private static AuthResponse getTokenFromJSONString(String responseBody) {
        try {
            return new ObjectMapper().readValue(responseBody, AuthResponse.class);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
