package pl.fairit.somedayiwill.newsletter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public enum NewsletterFrequency {
    NEVER("never"),
    WEEKLY("weekly"),
    MONTHLY("monthly");

    private String value;

    public String getValue() {
        return this.value;
    }
}
