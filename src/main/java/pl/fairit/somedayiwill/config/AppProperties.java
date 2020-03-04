package pl.fairit.somedayiwill.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final Auth auth = new Auth();
    private final Cron cron = new Cron();
    private final GoogleBooks googleBooks = new GoogleBooks();

    @Data
    public static class Auth {
        private String tokenSecret;
        private long tokenExpirationMills;
    }

    @Data
    private static class Cron {
        private String weeklyPattern;
        private String monthlyPattern;
    }

    @Data
    private static class GoogleBooks {
        private String apiKey;
        private String baseUrl;
    }
}
