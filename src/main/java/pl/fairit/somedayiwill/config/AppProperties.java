package pl.fairit.somedayiwill.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final Auth auth = new Auth();
    private final Cron cron = new Cron();
    private final GoogleBooks googleBooks = new GoogleBooks();
    private final MovieDatabase movieDatabase = new MovieDatabase();

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
        private String key;
        private String baseUrl;
    }

    @Data
    private static class MovieDatabase {
        private String key;
        private String baseUrl;
        private String posterBaseUrl;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
