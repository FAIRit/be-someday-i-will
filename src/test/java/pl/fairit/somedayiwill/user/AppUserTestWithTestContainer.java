package pl.fairit.somedayiwill.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.fairit.somedayiwill.security.user.SignupEmailService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@MockBean(SignupEmailService.class)
@ContextConfiguration
public class AppUserTestWithTestContainer {

    @Autowired
    public AppUserService service;

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
        var expectedUser = TestUsers.aDefaultUser();

        var actualUser = service.saveUser(expectedUser);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void existsByEmailShouldReturnFalseAfterDeleteUserPerformed() {
        var existingUser = service.getExistingUser(2L);

        service.deleteUser(existingUser.getId());
        var userExists = service.existsByEmail(existingUser.getEmail());

        assertFalse(userExists);
    }
}
