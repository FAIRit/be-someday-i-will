package pl.fairit.somedayiwill.security.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.fairit.somedayiwill.newsletter.NewsletterFrequency;
import pl.fairit.somedayiwill.user.AppUser;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    void signup() throws Exception {
        var signupRequest = new SignUpRequest("John", "john@doe.com", "Password1", NewsletterFrequency.WEEKLY);

        var userToReturn = new AppUser();

        when(authService.registerUser(signupRequest)).thenReturn(userToReturn);

        mockMvc.perform(post("/auth/signup"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully"));
    }

    @Test
    void login() {
        var loginRequest = new LoginRequest("john@doe.com", "Password1");

//        when(authService.authenticateUser(loginRequest)).thenReturn();
    }

}
