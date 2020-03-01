package pl.fairit.somedayiwill.mailsender;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public enum NewsletterFrequency {
    NEVER,
    WEEKLY("weekly"),
    MONTHLY("monthly");

    private String value;
}
