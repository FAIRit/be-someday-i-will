package pl.fairit.somedayiwill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.fairit.somedayiwill.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EnableScheduling
public class SomedayIWillApplication {

    public static void main(String[] args) {
        SpringApplication.run(SomedayIWillApplication.class, args);
    }

}
