package pl.fairit.somedayiwill.movie.usersmovies;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.user.AppUserRepository;
import pl.fairit.somedayiwill.user.AppUserService;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Testcontainers
@ContextConfiguration(initializers = {MovieTestContainerTest.Initializer.class})
public class MovieTestContainerTest {
    @Container
    public static MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer("mysql:8")
            .withDatabaseName("testdb")
            .withPassword("testDbPass")
            .withUsername("testDbUser")
            .withInitScript("db/migration/Test_Database.sql")
            .withStartupTimeout(Duration.ofMillis(2000));

    @Autowired
    public AppUserService service;

    @Test
    public void findUsersForMonthlyNewsletterShouldReturnOneUser() {
        var usersForMonthlyNewsletter = service.getAllUsersForMonthlyNewsletter();

        assertEquals(1, usersForMonthlyNewsletter.size());
    }

    @Test
    public void containerTest() {
        assertTrue(mySQLContainer.isRunning());
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mySQLContainer.getUsername(),
                    "spring.datasource.password=" + mySQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
