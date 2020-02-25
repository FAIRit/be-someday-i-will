package pl.fairit.somedayiwill.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final Auth auth = new Auth();

    @Data
    public static class Auth {
        private String tokenSecret;
        private long tokenExpirationMsec;
    }
}
