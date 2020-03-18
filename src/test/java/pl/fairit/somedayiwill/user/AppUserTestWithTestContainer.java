package pl.fairit.somedayiwill.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.fairit.somedayiwill.newsletter.NewsletterFrequency;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Testcontainers
@ContextConfiguration(initializers = {AppUserTestWithTestContainer.Initializer.class})
public class AppUserTestWithTestContainer {
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
    public void containerTest() {
        assertTrue(mySQLContainer.isRunning());
    }

    @Test
    public void findUsersForMonthlyNewsletterShouldReturnOneUser() {
        var usersForMonthlyNewsletter = service.getAllUsersForMonthlyNewsletter();

        assertEquals(1, usersForMonthlyNewsletter.size());
    }

    @Test
    public void findUsersForWeeklyNewsletterShouldReturnTwoUsers() {
        var usersForWeeklyNewsletter = service.getAllUsersForWeeklyNewsletter();

        assertEquals(2, usersForWeeklyNewsletter.size());
    }

    @Test
    public void shouldSaveUser() {
        var userToSave = AppUser.builder().name("Sam").email("sam@test.com").newsletterFrequency(NewsletterFrequency.NEVER).password("Passsword4").build();

        var savedUser = service.saveUser(userToSave);

        assertEquals(userToSave, savedUser);
    }

    @Test
    public void existsByEmailShouldReturnFalseAfterDeleteUserPerformed() {
        var existingUser = service.getExistingUser(1L);

        service.deleteUser(existingUser.getId());
        var userExists = service.existsByEmail(existingUser.getEmail());

        assertFalse(userExists);
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
