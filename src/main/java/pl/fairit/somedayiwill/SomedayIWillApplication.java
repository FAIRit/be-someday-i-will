package pl.fairit.somedayiwill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.fairit.somedayiwill.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class SomedayIWillApplication {

    public static void main(String[] args) {
        SpringApplication.run(SomedayIWillApplication.class, args);
    }

}
