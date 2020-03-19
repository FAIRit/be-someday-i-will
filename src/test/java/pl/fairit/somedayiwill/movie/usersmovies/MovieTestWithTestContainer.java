package pl.fairit.somedayiwill.movie.usersmovies;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.fairit.somedayiwill.user.AppUserService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class MovieTestWithTestContainer {

    @Autowired
    public AppUserService service;

    @Test
    public void findUsersForMonthlyNewsletterShouldReturnOneUser() {
        var usersForMonthlyNewsletter = service.getAllUsersForMonthlyNewsletter();

        assertEquals(1, usersForMonthlyNewsletter.size());
    }
}
