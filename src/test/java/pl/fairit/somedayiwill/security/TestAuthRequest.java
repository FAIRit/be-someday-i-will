package pl.fairit.somedayiwill.security;

import netscape.javascript.JSObject;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import pl.fairit.somedayiwill.user.AppUser;

import static java.util.Objects.nonNull;

public class TestAuthRequest {
    public static String retrieveLoginRequestBodyFromProvidedAppUser(final AppUser userToLogin) {
        var loginRequestBody = new JSONObject();
        try {
            loginRequestBody.put("email", userToLogin.getEmail());
            loginRequestBody.put("password", userToLogin.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return signupRequestBody.toString();
    }
}
