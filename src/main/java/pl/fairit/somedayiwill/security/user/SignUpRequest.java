package pl.fairit.somedayiwill.security.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import pl.fairit.somedayiwill.newsletter.NewsletterFrequency;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static java.util.Objects.nonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank
    @Length(min = 2, message = "Name has to be at at least 2 character long")
    private String name;

    @NotBlank
    @Email(message = "Invalid email")
    private String email;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private NewsletterFrequency newsletterFrequency;

    @JsonIgnore
    @AssertTrue(message = "Password has to be at least 8 characters and contain at least one digit and one upper case letter")
    public boolean isPasswordValid() {
        return nonNull(password) && password.length() >= 8 &&
                password.chars().anyMatch(Character::isDigit) &&
                password.chars().anyMatch(Character::isUpperCase);
    }
}
