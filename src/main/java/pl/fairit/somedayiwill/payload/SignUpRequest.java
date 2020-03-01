package pl.fairit.somedayiwill.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import pl.fairit.somedayiwill.mailsender.NewsletterFrequency;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static java.util.Objects.nonNull;

@Data
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private NewsletterFrequency newsletterFrequency;

    @JsonIgnore
    @AssertTrue(message = "Password has to be at least 8 characters and contain at least one digit")
    public boolean isPasswordValid() {
        return nonNull(password) && password.length() >= 8 &&
                password.chars().anyMatch(Character::isDigit);
    }
}
